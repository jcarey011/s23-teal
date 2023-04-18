const express = require('express');
const cors = require('cors');
const mysql = require('mysql2');

const app = express();
app.use(cors());
app.use(express.json());

const db = mysql.createConnection({
  host: 'YOUR_IP_V4_ADDRESS_HERE',
  user: 'team',
  password: 'teal',
  database: 'tealtest'
});

db.connect((err) => {
  if (err) {
    console.error('Error connecting to the database:', err.stack);
    return;
  }
  console.log('Connected to the database');
});

app.get('/', (req, res) => {
  res.send('Hello World!');
});

// Register a new user
app.post('/register', (req, res) => {
    console.log("Incoming request: ", req.body);
    const { first_name, last_name, email, password } = req.body;
  
    const query = 'INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)';
    db.query(query, [first_name, last_name, email, password], (err, result) => {
      if (err) {
        console.error(err);
        res.status(500).send('Error registering the user');
      } else {
        console.log("New user added: ", result);
        res.status(201).send('User registered successfully');
      }
    });
});
  
// Login
app.post('/login', (req, res) => {
  const { email, password } = req.body;

  const query = 'SELECT * FROM users WHERE email = ? AND password = ?';
  db.query(query, [email, password], (err, result) => {
    if (err) {
      console.error(err);
      res.status(500).send('Error logging in');
    } else if (result.length > 0) {
      res.status(200).send('Login successful');
    } else {
      res.status(401).send('Incorrect username or password');
    }
  });
});


app.listen(3000, () => {
  console.log('Server is running on port 3000');
});
