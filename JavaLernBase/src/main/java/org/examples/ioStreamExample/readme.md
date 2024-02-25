**Потоки ввода вывода**  
Иерархия классовых байтовых потоков:  

InputStream ->
- SequenceInputStream
- ByteArrayInputStream 
- PipedInputStream 
- ObjectInputStream 
- BlockDataInputStream 
- FileInputStream
- FilterInputStream
- PeekInputStream
- StringBufferInputStream  

OutputStream ->
- ByteArrayOutputStream
- FileOutputStream
- ObjectOutputStream
- BlockDataOutputStream
- FilterOutputStream
- PipedOutputStream  

Reader (Символьный) ->  
- FilterReader
- PipedReader
- StringReader
- CharArrayReader
- InputStreamReader
- BufferedReader  

Writer (Символьный) -> 
- CharArrayWriter
- StringWriter
- PrintWriter
- PipedWriter
- FileWriter
- BufferedWriter
- OutputStreamWriter