![Screenshot (1323)](https://user-images.githubusercontent.com/71547739/183851768-fbcc86e1-3493-4a59-b897-97afa79175fc.png)
1. add file chooser implementation to save and load file
implemented on code.

2. add save and load 2 using java nio
implemented on code.

3. add error handling for this application
   - list all error scenario that potentially happen ?
*Kalau semisal location pathnya ga ditemuin.
*Kalau semisal pake java nio terus mau load data, tapi ga jadi. Filenya kan jadi null

4. is it better to save data on plain text or json ? why ?
Kayanya kalau mau buat data exchange kaya antar server sama client bagus pake json

5. is there a better format to json to store data on plain text ?
   name a couple of alternatives (min 2)
XML,CSV

6. is it better to save data on file instead of a dedicated database ?
   is there a scenario where saving data on file be better ?
Kayanya kalau semisal kita mau archive file mending disimpen di file daripada di database karna archive file ga perlu akses yg cpt.

7. can you test if a specific file exist in a specified path with java ?
   how ?
Kayanya dideclare si variabel String buat simpn filenamenya trs dicek null/ngga

8  can you test if a specific  directory exist in a specified path ?
   how ?
implemented on prak code, jadi pathnya dideklarasi trs dicek null/ngga

9. can you delete or move file using java ? how ?
kayanya bisa di file itu ada method file.delete() sama file.renameTo()
(https://stackoverflow.com/questions/8208629/moving-file-to-new-location-then-deleting-the-previous-file)

10. can you think of potential security problem when you allow java to freely
   change file on your computer ? how do we prevent that ?
Mungkin bisa dibuat semacem file log buat nyimpen file yg sebelum diubah.
