
# File Compressor System


File compression is a data compression method in which the logical size of a file is reduced to save disk space for easier and faster transmission over a network or the Internet. It enables the creation of a version of one or more files with the same data at a size substantially smaller than the original file. Typically, file compression works by scanning an entire file, identifying similar or repetitive data and patterns and replacing duplicates with a unique identifier. This identifier is usually much smaller in size than the original word and consumes less space. Thus, the size of the compressed file is considerably smaller.

# Implemented Algorithms

#### 1.) Run Length Encoding :-
Run–length encoding (RLE) is a simple form of lossless data compression that runs on sequences with the same value occurring many consecutive times. It encodes the sequence to store only a single value and its count.

#### 2.) LZ77 :-
LZ77 is a dictionary based algorithm
In general only one coding scheme exists, all data will be coded in the same form, because each byte sequence is extended by the first symbol deviating from the former contents, the set of already used symbols will continuously grow. 

#### 3.) Huffman Encoding :-
Huffman coding first creates a tree using the frequencies of the character and then generates code for each character.Once the data is encoded, it has to be decoded. Decoding is done using the same tree.Huffman Coding prevents any ambiguity in the decoding process using the concept of prefix code ie. a code associated with a character should not be present in the prefix of any other code. The tree created above helps in maintaining the property.

## Helper Class

It has many important functions such as:-

a.) readFileAsString() - Reads the the reads the contents of the file into a byte array then converts it to a string which is returned to the required function.

b.) writeFile() - The encoding of string into the sequence of bytes and keeps it in an array of bytes which is stored in the file.

c.) calcFileSize() - Calculates the file size in bytes and kilobytes

d.) calculateCompression() - Returns the percentage of compression between input file and output file

e.) chooseFile() - This function returns the absolute pathname of the given file object.


## Screenshots

<img src="https://github.com/NeilNowgaonkar/File-Compression-Techniques-Project/blob/master/images/Dashboard.PNG?raw=true" alt="Dashboard Screenshot">

<img src="https://github.com/NeilNowgaonkar/File-Compression-Techniques-Project/blob/master/images/RLE%20GUI.PNG?raw=true" alt="RLE Screenshot">

<img src="https://github.com/NeilNowgaonkar/File-Compression-Techniques-Project/blob/master/images/LZ77%20Output.PNG?raw=true" alt="LZ77 Screenshot">

<img src="https://github.com/NeilNowgaonkar/File-Compression-Techniques-Project/blob/master/images/Huffman%20Output.PNG?raw=true" alt="Huffman Screenshot">
