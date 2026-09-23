import { useEffect, useState } from "react";
import BookDataService from "./services/book.service";
import "./App.css";

const emptyForm = { title: "", authorsText: "", year: "" };

function App() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [formOpen, setFormOpen] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [form, setForm] = useState(emptyForm);
  const [submitting, setSubmitting] = useState(false);
  const [deletingId, setDeletingId] = useState(null);

  const loadBooks = () => {
    setLoading(true);
    BookDataService.getAll()
      .then((response) => {
        setBooks(response.data);
        setError("");
      })
      .catch((err) => setError(describeError(err)))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    loadBooks();
  }, []);

  const describeError = (err) => {
    if (err.response?.data) return String(err.response.data);
    if (err.message === "Network Error") {
      return "Can't reach the server. Is the backend running on port 8080?";
    }
    return err.message;
  };

  const openAddForm = () => {
    setEditingId(null);
    setForm(emptyForm);
    setError("");
    setFormOpen(true);
  };

  const openEditForm = (book) => {
    setEditingId(book.id);
    setForm({
      title: book.title || "",
      authorsText: (book.authors && book.authors.length ? book.authors : [book.author].filter(Boolean)).join(", "),
      year: book.year ?? "",
    });
    setError("");
    setFormOpen(true);
  };

  const closeForm = () => {
    setFormOpen(false);
    setEditingId(null);
    setForm(emptyForm);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError("");

    const authors = form.authorsText
      .split(",")
      .map((a) => a.trim())
      .filter(Boolean);

    const payload = {
      title: form.title.trim(),
      authors,
      year: form.year === "" ? null : Number(form.year),
    };

    setSubmitting(true);
    const request = editingId
      ? BookDataService.update(editingId, payload)
      : BookDataService.create(payload);

    request
      .then(() => {
        closeForm();
        loadBooks();
      })
      .catch((err) => setError(describeError(err)))
      .finally(() => setSubmitting(false));
  };

  const handleDelete = (id) => {
    setDeletingId(id);
    BookDataService.delete(id)
      .then(loadBooks)
      .catch((err) => setError(describeError(err)))
      .finally(() => setDeletingId(null));
  };

  const handleDeleteAll = () => {
    if (!window.confirm("Delete every book? This can't be undone.")) return;
    BookDataService.deleteAll()
      .then(loadBooks)
      .catch((err) => setError(describeError(err)));
  };

  return (
    <div className="page">
      <header className="header">
        <div className="header-text">
          <p className="eyebrow">Book Library</p>
          <h1>Your Collection</h1>
          <p className="subtitle">
            {loading ? "Loading…" : `${books.length} ${books.length === 1 ? "book" : "books"} on the shelf`}
          </p>
        </div>
        <div className="header-actions">
          <button className="btn btn-ghost" onClick={handleDeleteAll} disabled={books.length === 0}>
            Clear all
          </button>
          <button className="btn btn-primary" onClick={openAddForm}>
            + Add book
          </button>
        </div>
      </header>

      {error && (
        <div className="banner banner-error">
          <span>{error}</span>
          <button className="banner-dismiss" onClick={() => setError("")} aria-label="Dismiss">
            ×
          </button>
        </div>
      )}

      {formOpen && (
        <div className="modal-backdrop" onClick={closeForm}>
          <form className="modal" onClick={(e) => e.stopPropagation()} onSubmit={handleSubmit}>
            <h2>{editingId ? "Edit book" : "Add a book"}</h2>

            <label className="field">
              <span>Title</span>
              <input
                autoFocus
                required
                value={form.title}
                onChange={(e) => setForm({ ...form, title: e.target.value })}
                placeholder="The Hobbit"
              />
            </label>

            <label className="field">
              <span>Author(s)</span>
              <input
                required
                value={form.authorsText}
                onChange={(e) => setForm({ ...form, authorsText: e.target.value })}
                placeholder="J.R.R. Tolkien"
              />
              <small>Separate multiple authors with commas</small>
            </label>

            <label className="field">
              <span>Year</span>
              <input
                type="number"
                required
                value={form.year}
                onChange={(e) => setForm({ ...form, year: e.target.value })}
                placeholder="1937"
              />
            </label>

            <div className="modal-actions">
              <button type="button" className="btn btn-ghost" onClick={closeForm}>
                Cancel
              </button>
              <button type="submit" className="btn btn-primary" disabled={submitting}>
                {submitting ? "Saving…" : editingId ? "Save changes" : "Add book"}
              </button>
            </div>
          </form>
        </div>
      )}

      <main>
        {loading ? (
          <div className="state-block">
            <div className="spinner" />
            <p>Loading your books…</p>
          </div>
        ) : books.length === 0 ? (
          <div className="state-block">
            <p className="state-title">Your shelf is empty</p>
            <p>Add your first book to get started.</p>
            <button className="btn btn-primary" onClick={openAddForm}>
              + Add book
            </button>
          </div>
        ) : (
          <div className="grid">
            {books.map((book) => {
              const authorNames =
                book.authors && book.authors.length ? book.authors : [book.author].filter(Boolean);
              return (
                <article className="card" key={book.id}>
                  <div className="card-body">
                    <span className="card-year">{book.year}</span>
                    <h3 className="card-title">{book.title}</h3>
                    <p className="card-author">{authorNames.join(", ") || "Unknown author"}</p>
                  </div>
                  <div className="card-actions">
                    <button className="btn btn-small btn-ghost" onClick={() => openEditForm(book)}>
                      Edit
                    </button>
                    <button
                      className="btn btn-small btn-danger"
                      onClick={() => handleDelete(book.id)}
                      disabled={deletingId === book.id}
                    >
                      {deletingId === book.id ? "…" : "Delete"}
                    </button>
                  </div>
                </article>
              );
            })}
          </div>
        )}
      </main>
    </div>
  );
}

export default App;
