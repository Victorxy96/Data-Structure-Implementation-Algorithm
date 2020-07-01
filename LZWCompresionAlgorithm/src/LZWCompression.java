import java.io.*;

/**
 * LZWCompression class allows the users to compress and decompress the files using LZW compression algorithm. The
 * Algorithm is both working for ASCII files and binary files.
 *
 */
public class LZWCompression {
    /**
     * Store 12 bits for buffer
     */
    private byte[] buffer = new byte[3];
    /**
     * buffer size
     */
    private int bufferSize = 0;
    /**
     * HashMap used for compress
     */
    private MyHashMap<String, Integer> compressMap;
    /**
     * HashMap used for decompress
     */
    private MyHashMap<Integer, String> decompressMap;
    /**
     * Stores the information from the compressed file
     */
    private MyLinkedList<Integer> codes = new MyLinkedList<>();

    /**
     * Read and compress the file.
     * @param inputFile input file
     * @param outputFile output file
     * @throws IOException exception
     */
    public void compress(String inputFile, String outputFile) throws IOException {
        // generate io stream
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));

        // create the hashMap for compression
        int size = 256;
        compressMap = new MyHashMap<>(size);
        for(int i=0;i<compressMap.getSize();i++) {
            compressMap.put(Character.toString((char)i),i);
        }

        // read files
        byte inputByte;
        char inputCharacter;
        String string = "";

        try {
            // read the data stream
            inputByte = dataInputStream.readByte();
            // remove extra bits
            inputCharacter = (char)(inputByte & 0xFF);
            string = string+inputCharacter;

            while(true) {
                inputByte = dataInputStream.readByte();
                // reset the hashMap when exceed the limit
                if(size == 4096) {
                    size = 256;
                    compressMap = new MyHashMap<>(256);
                    for(int i=0;i<compressMap.getSize();i++) {
                        compressMap.put(Character.toString((char)i),i);
                    }
                }

                // remove extra bits
                inputCharacter = (char) (inputByte & 0xFF);
                // if hashMap contains key
                if(compressMap.containsKey(string+inputCharacter)) {
                    string = string+inputCharacter;
                } else {
                    // write to chunk
                    writeToChunk(string, dataOutputStream,false);
                    // put k-v pairs to hashMap
                    compressMap.put(string+inputCharacter,size);
                    size++;
                    string = Character.toString(inputCharacter);
                }
            }
        } catch (EOFException e) {
            dataInputStream.close();
        }

        // close the output stream when finish write the last all bits to chunk
        if(string.equals("")) {
            writeToChunk(string,dataOutputStream,true);
        }
        dataOutputStream.close();
    }

    /**
     * Wirte the bits to the file in chunk
     * @param s string
     * @param outputStream output stream
     * @param meetLast whether meet last bits
     * @throws IOException exception
     */
    private void writeToChunk(String s,DataOutputStream outputStream, boolean meetLast) throws IOException {
        // if meet null value just return
        if(compressMap.get(s)==null) {
            return;
        }
        // get the binary string
        String stringBinary = Integer.toBinaryString(compressMap.get(s));

        // extend the string length to 12 with leading zeros
        int length = stringBinary.length();
        for(int i=length;i<12;i++) {
            stringBinary = "0"+stringBinary;
        }

        // the string to write to chunk
        String stringToWrite = "";

        // write bits to the chunk with 12-bit chunk
        // if buffer size is 0
        if(bufferSize == 0) {
            stringToWrite = stringBinary.substring(0,8);
            buffer[0] = (byte) Integer.parseInt(stringToWrite,2);
            stringToWrite = stringBinary.substring(8,12)+"0000";
            buffer[1] = (byte) Integer.parseInt(stringToWrite,2);
            if(meetLast == true) {
                outputStream.writeByte(buffer[0]);
            }
            bufferSize++;
        } else {
            // if buffer size is not zero
            stringToWrite = stringBinary.substring(0,4);
            buffer[1] += (byte) (Integer.parseInt(stringToWrite,2));
            stringToWrite = stringBinary.substring(4,12);
            buffer[2] = (byte) Integer.parseInt(stringToWrite,2);
            // write and reset the buffer
            for(int i=0;i<3;i++) {
                outputStream.writeByte(buffer[i]);
                buffer[i] = 0;
            }
            bufferSize = 0;
        }
    }

    /**
     * Decompress the file.
     * @param inputFile input file
     * @param outputFile output file
     * @throws IOException exception
     */
    public void decompress(String inputFile, String outputFile) throws IOException {
        // input data stream
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
        // output stream
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));

        // create the hashMap for decompression
        int size = 256;
        decompressMap = new MyHashMap<>(256);
        for (int i=0;i<decompressMap.getSize();i++) {
            decompressMap.put(i, Character.toString((char) i));
        }

        // read file, save the information to linked list and reset the linked list
        readFile(dataInputStream);
        codes.reset();

        // the former code in the linked list
        int formerCode = codes.next();
        // current code in the linked list
        int code = 0;

        // update the decompress Map
        writeCodes(decompressMap.get(formerCode),dataOutputStream);

        String newString = "";
        while (codes.hasNext()) {
            // reset the hashMap when exceed the limit
            if(size == 4096) {
                decompressMap = new MyHashMap<>(256);
                for (int i=0;i<decompressMap.getSize();i++) {
                    decompressMap.put(i, Character.toString((char)i));
                }
                size = 256;
            }
            code = codes.next();
            // if hashmap contains the code
            if(!decompressMap.containsKey(code)) {
                if(decompressMap.get(formerCode) == null) {
                    continue;
                }
                newString = decompressMap.get(formerCode)+decompressMap.get(formerCode).charAt(0);
                decompressMap.put(size,newString);
                writeCodes(newString,dataOutputStream);
            } else {
                // if not
                newString = decompressMap.get(formerCode)+decompressMap.get(code).charAt(0);
                decompressMap.put(size,newString);
                writeCodes(decompressMap.get(code),dataOutputStream);
            }
            size++;
            // update former code
            formerCode = code;
        }
        // close the output data stream when finish processing the last bit
        dataOutputStream.close();
    }

    /**
     * Read file.
     * @param inputStream input stream
     * @throws IOException exception
     */
    private void readFile(DataInputStream inputStream) throws IOException {

        try{
            while (true) {
                // remove extra bits, read the strings with leading zeros
                // add the string to the linked list
                String stringA = addZeros(Integer.toBinaryString(inputStream.readByte() & 0xFF));
                String stringB = addZeros(Integer.toBinaryString(inputStream.readByte() & 0xFF));
                codes.addAtTail(Integer.parseInt(stringA + stringB.substring(0, 4), 2));
                String stringC = addZeros(Integer.toBinaryString(inputStream.readByte() & 0xFF));
                codes.addAtTail(Integer.parseInt(stringB.substring(4,8)+stringC,2));
            }
        } catch (EOFException e) {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove extra bits and make the string length is 8 with leading zeros.
     * @param input input string
     * @return string length is 8 with leading zeros
     */
    private String addZeros(String input) {
        String noSignString = new String(input);
        while (noSignString.length()<8) {
            noSignString = "0"+noSignString;
        }
        return noSignString;
    }

    /**
     * Write codes to files.
     * @param input input stream
     * @param dataOutputStream data output stream
     * @throws IOException exception
     */
    private void writeCodes(String input, DataOutputStream dataOutputStream) throws IOException {
        for(char c: input.toCharArray()) {
            dataOutputStream.writeByte(c);
        }
    }

    /**
     * The main method.
     * The file size of words.html after compression is 1070355 bytes, the compression degree is 57.57%
     * The file size of CrimeLatLonXY.csv after compression is 1283748 bytes, the compression degree is 50.8%
     * The file size of 01_Overview.mp4 after compression is 33773772 bytes, the compression degree is -35.04%
     * @param args agrs
     */
    public static void main(String[] args) {
        LZWCompression lzw = new LZWCompression();
        // check the args length
        if (args.length == 3 || args.length == 4) {
            try {
                // compress file
                if (args[0].equals("-c")) {
                    if(args.length == 3) {
                        lzw.compress(args[1], args[2]);
                    }
                    // check the detail bytes
                    if(args.length == 4) {
                        lzw.compress(args[2], args[3]);
                        File infile = new File(args[2]);
                        File outfile = new File(args[3]);
                        System.out.printf("bytes read = %d , bytes written = %d", infile.length(), outfile.length());
                    }
                } else if (args[0].equals("-d")) {
                    // decompress file
                    if(args.length == 3) {
                        lzw.decompress(args[1], args[2]);
                    }
                    // check the detail bytes
                    if(args.length == 4) {
                        lzw.decompress(args[2], args[3]);
                        File infile = new File(args[2]);
                        File outfile = new File(args[3]);
                        System.out.printf("bytes read = %d , bytes written = %d", infile.length(), outfile.length());
                    }
                } else {
                    System.out.println("Error Please Enter Again");
                    System.exit(0);
                }
            } catch (IOException e) {
                System.out.println("Error Please Enter Again");
                System.exit(0);
            }
        } else {
            System.out.println("Error Please Enter Again");
            System.exit(0);
        }
    }
}
