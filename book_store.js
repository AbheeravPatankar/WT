const express = require('express');
const cors = require('cors');
const mysql = require('mysql2');

const app = express();

app.use(cors());

const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '1234',
  database: 'WT'
});

connection.connect(error => {
    if(error){
        console.log(error);
    }
    console.log("database connected ....")
})
// JSON parsing middleware - this is essential!
app.use(express.json());

class Book {
    constructor(name, author, price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }
}

book1 = new Book("Let Us C", "yashwant", "50");
book2 = new Book("Man Eators of Kumaon", "Jim", "100");
book3 = new Book("Murder on the nile", "agatha", "150");
book4 = new Book("Orient Express", "agatha", "500");

books = [];

app.get('/', (req, res) => {
    console.log("server initialised.. ");
    res.send("Server is up!");
});

app.post('/add_book', (req, res) => {
    console.log('printing the req body ...');
    console.log(req.body);
    books.push(req.body);

    book_name = req.body.name;
    book_author = req.body.author;
    book_price = req.body.price;

    query = "insert into WT.books (book_name, book_author, book_price) values('" + book_name + "','" + book_author + "','" + book_price + "');";
    connection.query(query);
    console.log(query);
});

app.get('/books', (req, res) => {
    connection.query("SELECT * FROM books", (err, results, fields) => {
        if (err) {
            console.error("Error executing query:", err);
            return res.status(500).json({ error: "Database error" });
        }

        const books = results.map(row => new Book(row.book_name, row.book_author, row.book_price));
        console.log("Sending books:", books);
        res.json(books);  
    });
});


console.log("starting the server ...");
app.listen(3000);
