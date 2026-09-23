import { useEffect, useState } from "react";
import BookDataService from "./services/book.service";

function App() {
  const [books, setBooks] = useState([]);
  const [form, setForm] = useState({ title: "", author: "", year: "" });
  const [error, setError] = useState("");

  const loadBooks = () => {
    BookDataService.getAll()
        .then((response) => setBooks(response.data))
        .catch((err) => setError(err.message));
  };

  useEffect(() => {
    loadBooks();
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    BookDataService.create({ ...form, year: Number(form.year) })
        .then(() => {
          setForm({ title: "", author: "", year: "" });
          setError("");
          loadBooks();
        })
        .catch((err) => setError(err.response?.data || err.message));
  };

  const handleDelete = (id) => {
    BookDataService.delete(id)
        .then(loadBooks)
        .catch((err) => setError(err.message));
  };

  return (
      <div style={{ maxWidth: 600, margin: "40px auto", fontFamily: "sans-serif" }}>
        <h1>Book Library</h1>

        {error && <p style={{ color: "red" }}>{String(error)}</p>}

        <ul>
          {books.map((b) => (
              <li key={b.id} style={{ marginBottom: 8 }}>
                {b.label} by {b.author}{" "}
                <button onClick={() => handleDelete(b.id)}>Delete</button>
              </li>
          ))}
        </ul>

        <h2>Add a book</h2>
        <form onSubmit={handleSubmit}>
          <input
              placeholder="Title"
              value={form.title}
              onChange={(e) => setForm({ ...form, title: e.target.value })}
          />
          <input
              placeholder="Author"
              value={form.author}
              onChange={(e) => setForm({ ...form, author: e.target.value })}
          />
          <input
              placeholder="Year"
              type="number"
              value={form.year}
              onChange={(e) => setForm({ ...form, year: e.target.value })}
          />
          <button type="submit">Add</button>
        </form>
      </div>
  );
}

export default App;