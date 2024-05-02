import { Link } from "react-router-dom"
import React from "react"

const NavBar = () => (
  <nav className="bg-sky-900 mb-4">
    <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
      <div className="relative flex h-16 items-center justify-between">
        <div className="flex flex-1 items-center justify-center ">
          <div className="flex flex-shrink-0 items-center">
            <Link
              to="/"
              className="text-white hover:text-blue-600 py-2 text-2xl font-semibold"
            >
              poly-yard
            </Link>
          </div>
        </div>
      </div>
    </div>
  </nav>
)

export default NavBar
