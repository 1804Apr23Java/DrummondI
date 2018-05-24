/**
 * 
 */
let img = document.getElementById('imgView');
let s = "../image/?id=" + img.getAttribute('alt');

img.setAttribute('src', s);

let delteButton = document.getElementById('deleteButton');
deleteButton.addEventListener('click', (e) => {
	let xhr = new XMLHttpRequest();
	xhr.open('DELETE', '/project1/request/?id=' + img.getAttribute('alt'), true);
	 
	xhr.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200) {
			window.location.href = '/project1/front/eview';
		}
	}
	 
	xhr.send(null);
});