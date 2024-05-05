import { Route, Routes } from "react-router-dom"
import React from "react"
import App from "./App"
import BookDetails from "./book/BookDetails"

const Router = () => (
  <Routes>
    <Route path="/" element={<App />} />
    <Route path="/book/:bookId" element={<BookDetails />} />
  </Routes>
)

export default Router
