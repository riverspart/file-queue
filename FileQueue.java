public class FileQueue {
        private long mSize = 0;
        private String mDataFile;
        private BufferedReader mIs;
        private BufferedWriter mOs;
        
        public void removeFirstLine(String fileName) throws IOException {  
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");          
             //Initial write position                                             
            long writePosition = raf.getFilePointer();                            
            raf.readLine();                                                       
            // Shift the next lines upwards.                                      
            long readPosition = raf.getFilePointer();                             
        
            byte[] buff = new byte[1024];                                         
            int n;                                                                
            while (-1 != (n = raf.read(buff))) {                                  
                raf.seek(writePosition);                                          
                raf.write(buff, 0, n);                                            
                readPosition += n;                                                
                writePosition += n;                                               
                raf.seek(readPosition);                                           
            }                                                                     
            raf.setLength(writePosition);                                         
            raf.close();                                                          
        }         

        public FileQueue(String path) {
            try {
                mDataFile = path;
                File f = new File(mDataFile);
                f.createNewFile();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        public void destroy() {
            try {
                mIs.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        
        public void enqueue(String val) {
            
            try {
                mOs = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(mDataFile, true)));
                ++mSize;
                System.out.println("enqueue: " + val);
                mOs.write(val);
                mOs.newLine();
                mOs.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        public String top() {
            try {
                mIs = new BufferedReader(new FileReader(mDataFile));
                return mIs.readLine();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
        public String dequeue() {
            try {
                mIs = new BufferedReader(new FileReader(mDataFile));
                --mSize;
                String val = mIs.readLine();
                System.out.println("dequeue: " + val);
                removeFirstLine(mDataFile);
                return val;
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
        public boolean isEmpty() {
            return mSize <= 0;
        }
    }
