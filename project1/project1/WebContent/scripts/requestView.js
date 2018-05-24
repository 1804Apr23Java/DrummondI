let requestContainer = document.getElementById('requests');
let xhr = new XMLHttpRequest();
xhr.open('GET', '/project1/employee', true);
 
xhr.onreadystatechange = function() {
	if(this.readyState == 4 && this.status == 200) {
		 let reqArray = JSON.parse(this.responseText).requests;
		 
		 for(let i = 0; i < reqArray.length; i++) {
			let r = reqArray[i];
			
			let li = document.createElement('LI');
			li.setAttribute('class', 'list-group-item');
			let a = document.createElement('A');
			a.setAttribute('href', '../request/?id=' + r.r_id);
			a.innerHTML = 'Amount: $ ' + new Number(r.amt).toFixed(2) + '     Status: ' + r.stat + '     Date: ' + r.date;
			li.appendChild(a);
			
			requestContainer.appendChild(li);
		 }
	 }
 }
 
 xhr.send(null);