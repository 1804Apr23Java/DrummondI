class Book {
	constructor(title, authorFirstname, authorLastname, genre, year, pages) {
		this.title = title;
		this.authorFirstname = authorFirstname;
		this.authorLastname = authorLastname;
		this.year = year;
		this.pages = pages;
		this.genre = genre;
	}
	
	getStringRep() {
		return this.title + " by " + this.authorFirstname + " " + this.authorLastname;
	}
}

console.log('here');
let books  = [ new Book('The Lord of the Rings', 'JRR', 'Tolkien', 'Fantasy', '1955', '1000'),
			   new Book('Neuromancer', 'William', 'Gibson', 'Science Fiction', '1982', '383'),
			   new Book('Fahrenheit 451', 'Ray', 'Bradbury', 'Science Fiction', '1951', '343'),
			   new Book('The Sun Also Rises', 'Ernest', 'Hemmingway', 'Literature', '1948', '123'),
			   new Book('Harry Potter and the Sorcerers Stone', 'JK', 'Rowling', 'Fantasy', '1999', '300')
			 ];

let bookshelf = document.getElementById('bookshelf');
for(let i = 0; i < books.length; i++) {
	
	let newBook = document.createElement('DIV');
	newBook.setAttribute('class', 'card');
	newBook.setAttribute('style', 'width: 18rem;');
	
	let img = document.createElement('IMG');
	img.setAttribute('class', 'card-img-top');
	
	
	img.setAttribute('src', '../images/' + books[i].title + '.jpg');
	newBook.appendChild(img);
	
	let div2 = document.createElement('DIV');
	div2.setAttribute('class', 'card-body');
	newBook.appendChild(div2);
	
	let h5 = document.createElement('H5');
	h5.setAttribute('class', 'card-title');
	h5.innerHTML = books[i].getStringRep();
	div2.appendChild(h5);
	
	let p = document.createElement('P');
	p.setAttribute('class', 'card-text');
	p.innerHTML = books[i].year + "/n" + books[i].genre;
	div2.appendChild(p);
	
	let a = document.createElement('A');
	p.setAttribute('class', 'btn btn-primary');
	p.setAttribute('href', "/" + books[i].title);
	p.innerHTML = "View Book";
	div2.appendChild(p);
	
	bookshelf.appendChild(newBook);
}