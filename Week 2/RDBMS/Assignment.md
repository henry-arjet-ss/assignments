# SQL Exercise
*Henry Arjet*
*SS Essentials Week 2*
*July Cloud Engineering*



## 1.	How many copies of the book titled The Lost Tribe are owned by the library branch whose name is "Sharpstown"?
>0 
```
SELECT book.title, book.bookId, copies.branchId, copies.noOfCopies, branches.branchName FROM tbl_book AS book 
INNER JOIN tbl_book_copies AS copies ON  book.bookId = copies.bookId
INNER JOIN tbl_library_branch AS branches ON copies.branchId  = branches.branchId
WHERE book.title = "The Lost Tribe";
```
>I could add an "AND branches.branchName = Sharpstown" but that  would pull 0 results

## 2.	How many copies of the book titled The Lost Tribe are owned by each library branch?
>The book is only owned by Arkansas, which has 100 copies
```
SELECT book.title, book.bookId, copies.branchId, copies.noOfCopies, branches.branchName FROM tbl_book AS book 
INNER JOIN tbl_book_copies AS copies ON  book.bookId = copies.bookId
INNER JOIN tbl_library_branch AS branches ON copies.branchId  = branches.branchId
WHERE book.title = "The Lost Tribe";
```

## 3.	Retrieve the names of all borrowers who do not have any books checked out .
>There's 69 of them, I'll put them at the end
```
SELECT borrower.name 
FROM tbl_borrower borrower
LEFT JOIN tbl_book_loans loans on borrower.cardNo = loans.cardNo
WHERE loans.cardNo IS NULL
```

## 4.	For each book that is loaned out from the "Sharpstown" branch and whose DueDate is today, retrieve the book title, the borrower's name, and the borrower's address.
>All the dates are from 2018, and no two are due on the same day. In order to actually pull a row, I'll pretend today is March 14th 2018 because *12 Years A Slave* is one of only two books on this list I have read

```
SELECT book.title, borrower.name, borrower.address 
FROM tbl_library_branch branch
INNER JOIN tbl_book_loans loans ON branch.branchId = loans.branchId
INNER JOIN tbl_borrower borrower ON loans.cardNo = borrower.cardNo 
INNER JOIN tbl_book book ON loans.bookId = book.bookId
WHERE branch.branchName = "Sharpstown" 
AND CAST(loans.dueDate as DATE) = '2018-03-14*'
ORDER BY loans.dueDate
```
Solomon Northup's Odyssey |||	West Edmondson	||| 00825 Melrose Plaza



## 5.	For each library branch, retrieve the branch name and the total number of books loaned out from that branch.
>Too many to list here, again I have put them at the end
```
SELECT branch.branchName, count(*) as booksLoaned
FROM tbl_library_branch branch
INNER JOIN tbl_book_loans loans ON branch.branchId = loans.branchId
GROUP BY branch.branchName
```

## 6.	Retrieve the names, addresses, and number of books checked out for all borrowers who have more than five books checked out. 
Derron Larking || 3051 Elgar Hill || 6

Calida Radage || 7569 Mendota Terrace || 6

Baird Ruttgers || 2 Forest Run Park || 6

Ricky Hurndall || 7 Stoughton Junction || 6

```
SELECT borrower.name, borrower.address, count(*) as booksLoaned
FROM tbl_book_loans loans
INNER JOIN tbl_borrower borrower ON loans.cardNo = borrower.cardNo
GROUP BY loans.cardNo
HAVING booksLoaned > 5
```

## 7.	 For each book authored (or co-authored) by "Stephen King", retrieve the title and the number of copies owned by the library branch whose name is "Central"
>There are none. This table only has each book owned by one branch. In this case the three King books are owned by Homewood, Steensland, and Hoard. Central owns none.
```
SELECT book.title, copies.noOfCopies
FROM tbl_author AS author
INNER JOIN tbl_book book ON book.authId = author.authorId
INNER JOIN tbl_book_copies copies ON book.bookId = copies.bookId
INNER JOIN tbl_library_branch branch ON copies.branchId = branch.branchId
WHERE author.authorName = "Stephen King"  AND branch.branchName = "Central"
```

# Appendix

## Question 3 results

Marsh Stud

Alaine Galway

Sondra Peasey

Stinky Radbond

Carl MacAlaster

Brant Bartholat

Sella Penwarden

Frances Yitzowitz

Bree Scholtis

Bale Seath

Yehudi McMurdo

Adrienne Collishaw

Jyoti Breitling

Andriana Andersson

Troy Sawdy

Dinah Suddock

Isadora Pestell

Margette Swigger

Malinde Shimony

Emmalynne Mansford

Chere Hexam

Nico Dovidian

Garret Hawkin

Stacee Casaccia

Rhody Windows

Katti Swin

Tallia Bunney

Rutledge Capoun

Seymour Jewers

Kimberley Efford

Fawne Persey

Nester Layzell

Beverie Longhirst

Had Dimitrov

Conchita Baddoe

Skippie Lilian

Welch Wraxall

Yancy Febre

Roselle Chellenham

Allene Glozman

Sephira Da Costa

Matelda Girardet

Jareb Stonehewer

Bette-ann Lumley

Madelon Craggs

Frederigo Jumont

Marshall Tillard

Delmor Wittier

Erik Gave

Peri Salla

Leshia McGuirk

Haley M'Chirrie

Ninette Hernik

Layla Dimond

Danila Janiak

Walt Chandlar

Cassie Minet

Calvin Cecely

Jordain Taplin

Benito Buckleigh

Hillier Bayfield

Mord Holah

Andre Holborn

Son Korb

Serena Videneev

Wren Crossland

Rozele Casotti

Kerrin Ludy

Joice Northall

## Question 5 results
Arkansas 16

Atwood 23

Banding 16

Barnett 30

Bartelt 26

Birchwood 18

Blaine 14

Boyd 21

Cambridge 20

Central Clyde Gallagher 19

Dakota 24

David 15

Derek 7

Eagle Crest 19

Farragut 25

Forest 21

Hauk 17

Hoard 21

Hoffman 17

Homewood 20

Jackson 19

Jenifer 31

Kenwood  14

Knutson 25

Longview 23

Marquette 26

Mayfield  14

Menomonie 26

Nelson 17

Northview 24

Pierstorff 31

Prentice 20

Red Cloud 19

Redwing 17

Ridge Oak 16

Ruskin 15

Scofield 24

Sharpstown 15

Sommers 14

Southridge 8

Steensland 24

Stoughton 21

Sunbrook 12

Tennessee 29

Thackeray 15

Trailsway 19

Transport 30

Twin Pines 23

Victoria 19
