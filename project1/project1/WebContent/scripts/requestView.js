 let requestContainer = document.getElementById('requests');
 console.log("hello");
 let xhr = new XMLHttpRequest();
 xhr.open('GET', '/project1/employee', true);
 
 xhr.onreadystatechange = function() {
	 if(this.readyState == 4 && this.status == 200) {
		 
		 console.log(this.responseText);
		 
		 let reqs = JSON.parse(this.responseText);
		 
		 let reqArray = reqs.requests;
		 
		 for(let i = 0; i < reqArray.length; i++) {
			 let r = reqArray[i];
			 
			 let a = document.createElement('A');
			 a.setAttribute('href', '../../request/' + r.r_id);
			 a.setAttribute('class', 'list-group-item list-group-item-action');
			 a.innerHTML = 'Request for $' + new Number(r.amt).toFixed(2) + ' made on ' + r.date + ", status: " + r.stat;
			 
			 requestContainer.appendChild(a);
		 }
	 }
 }
 
 xhr.send(null);
 
 requestContainer.appendChild(document.createElement('P').innerHTML = 'hi');