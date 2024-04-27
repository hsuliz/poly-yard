import React from "react"
import ReactDOM from "react-dom/client"
import App from "./App"
import Book from "./book/Book"
import "./index.css"

const root = ReactDOM.createRoot(document.getElementById("root") as HTMLElement)
root.render(
  <React.StrictMode>
    <App />
    <Book />
  </React.StrictMode>,
)
