/**
 * 
 */
let requestContainer = document.getElementById('requests');
let xhr = new XMLHttpRequest();
xhr.open('GET', '/project1/img', true);
 
xhr.onreadystatechange = function() {
	if(this.readyState == 4 && this.status == 200) {
		 let reqArray = JSON.parse(this.responseText).employees;
		 
		 for(let i = 0; i < reqArray.length; i++) {
			let r = reqArray[i];
			
			let li = document.createElement('LI');
			li.setAttribute('class', 'list-group-item');
			
			let a = document.createElement('A');
			a.setAttribute('href', '../employeeHandle/?id=' + r.e_id + '&un=' + r.e_un + '&fn=' + r.e_fn + '&ln=' + r.e_ln + '&em=' +r.e_em);
			a.innerHTML = 'Employee #' + r.e_id + '-- Name: ' + r.e_fn + ' ' + r.e_ln + ' Username: ' + r.e_un + ' Email: ' + r.e_em;
			
			li.appendChild(a);
			requestContainer.appendChild(li);
		 }
	 }
 }
 
 xhr.send(null)