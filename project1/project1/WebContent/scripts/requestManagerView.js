/**
 * 
 */
let requestContainer = document.getElementById('requests');
let xhr = new XMLHttpRequest();
xhr.open('GET', '/project1/create', true);

xhr.onreadystatechange = function() {
	if(this.readyState == 4 && this.status == 200) {
		let users = JSON.parse(this.responseText).users;
		for(let j = 0; j < users.length; j++) {
			
			let ul = document.createElement('UL');
			ul.setAttribute('class', 'list-group');
			let li2 = document.createElement('LI');
			li2.setAttribute('class', 'list-group-item');
			li2.innerHTML = 'Employee # ' + users[j].requests[0].e_id;
			ul.appendChild(li2);
			
			let reqArray = users[j].requests;
			 
			 for(let i = 0; i < reqArray.length; i++) {
				let r = reqArray[i];
				
				let li = document.createElement('LI');
				li.setAttribute('class', 'list-group-item');
				let a = document.createElement('A');
				a.setAttribute('href', '../request/?id=' + r.r_id);
				a.innerHTML = 'Amount: $ ' + new Number(r.amt).toFixed(2) + ' Status: ' + r.stat + '     Date: ' + r.date;
				li.appendChild(a);
				
				let span2 = document.createElement('BUTTON');
				span2.setAttribute('class', 'btn btn-success');
				span2.setAttribute('id', 'btnB');
				span2.innerHTML = 'Approve';
				span2.addEventListener('click', (e) => {
					let xhr3 = new XMLHttpRequest();
					xhr3.open('POST', '/project1/request/?id=' + r.r_id + '&stat=' + 'a', true);
					 
					xhr3.onreadystatechange = function() {
						if(this.readyState == 4 && this.status == 200) {
							window.location.href = '/project1/front/allEview';
						}
					}
					 
					xhr3.send(null);
				});
				li.appendChild(span2);
				
				let span3 = document.createElement('BUTTON');
				span3.setAttribute('class', 'btn btn-warning');
				span3.setAttribute('id', 'btnB');
				span3.innerHTML = 'Deny';
				span3.addEventListener('click', (e) => {
					let xhr3 = new XMLHttpRequest();
					xhr3.open('POST', '/project1/request/?id=' + r.r_id + '&stat=' + 'd', true);
					 
					xhr3.onreadystatechange = function() {
						if(this.readyState == 4 && this.status == 200) {
							window.location.href = '/project1/front/allEview';
						}
					}
					 
					xhr3.send(null);
				});
				li.appendChild(span3);
				
				let span = document.createElement('BUTTON');
				span.setAttribute('class', 'btn btn-danger');
				span.setAttribute('id', 'btnB');
				span.innerHTML = 'Delete';
				span.addEventListener('click', (e) => {
					let xhr = new XMLHttpRequest();
					xhr.open('DELETE', '/project1/request/?id=' + r.r_id, true);
					 
					xhr.onreadystatechange = function() {
						if(this.readyState == 4 && this.status == 200) {
							window.location.href = '/project1/front/allEview';
						}
					}
					 
					xhr.send(null);
				});
				
				li.appendChild(span);
				ul.appendChild(li);
			 }
			 requestContainer.append(ul);
		}
	 }
 }
 
 xhr.send(null);