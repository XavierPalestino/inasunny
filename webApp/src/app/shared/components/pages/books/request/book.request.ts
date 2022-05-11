export interface BooksRequest {
    title: string
    pages: number
    isbn: string
    price: number
    summary: string
    editorial: string
    datePublication: Date
    categories: number[]
    authors: number[]
}