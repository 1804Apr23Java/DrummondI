/**
 * 
 */
let del = document.getElementById('delete');
let s = del.getAttribute('name');

del.addEventListener('click', (e) => {
	
	let xhr = new XMLHttpRequest();
	console.log('/project1/employeeHandle/?id=' + s);
	xhr.open('DELETE', '/project1/employeeHandle/?id=' + s, true);
	
	xhr.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200) {
			window.location.href = '/project1/front/allEmp';
		}
	};
	 
	xhr.send(null);
});