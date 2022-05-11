export interface BooksDTO {
    id: number
    title: string
    pages: number
    isbn: string
    price: number
    editorial: string
    summary: string
    releaseDate: Date
    authors: number[]
    categories: number[]
}