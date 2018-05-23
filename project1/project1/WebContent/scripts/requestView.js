 let requestContainer = document.getElementById('requests');
 console.log("hello");
 let xhr = new XMLHttpRequest();
 xhr.open('GET', '/project1/employee', true);
 
 xhr.onreadystatechange = function() {
	 if(this.readyState == 4 && this.status == 200) {
		 
		 let reqs = JSON.parse(this.responseText);
		 
		 let reqArray = reqs.requests;
		 
		 for(let i = 0; i < reqArray.length; i++) {
			let r = reqArray[i];
			
			let div1 = document.createElement('DIV');
			div1.setAttribute('class', 'card text-center');
			div1.setAttribute('style', "width: 18rem;");
			
			let img = document.createElement('IMG');
			img.setAttribute('class', 'card-img-top');
			div1.appendChild(img);
				
			let div2 = document.createElement('DIV');
			div2.setAttribute('class', 'class-body');
			div1.appendChild(div2);
				
			let h5 = document.createElement('H5');
			h5.setAttribute('class', 'card-title');
			h5.innerHTML = 'Request #' + r.r_id;
			div2.appendChild(h5);
			
			let p = document.createElement('P');
			p.setAttribute('class', 'card-text');
			p.innerHTML = '$ ' + new Number(r.amt).toFixed(2);
			div2.appendChild(p);
					
			let ul = document.createElement('UL');
			ul.setAttribute('class', 'list-group list-group-flush');
			div1.appendChild(ul);
			
			let li = document.createElement('LI');
			li.setAttribute('class', 'list-group-item');
			li.innerHTML = 'Submitted: ' + r.date;
			ul.appendChild(li);
			
			let li2 = document.createElement('LI');
			li2.setAttribute('class', 'list-group-item');
			li2.innerHTML = 'Status: ' + r.stat;
			ul.appendChild(li2);
				
			let div3 = document.createElement('DIV');
			div3.setAttribute('class', 'card-body');
			div1.appendChild(div3);
			
			let a = document.createElement('A');
			a.setAttribute('class', 'card-link');
			a.setAttribute('href', '../request/' + r.r_id);
			a.innerHTML = 'Update Request';
			div3.appendChild(a);
			
			let button = document.createElement('A');
			button.setAttribute('class', 'card-link btn-danger');
			div3.appendChild(button);
			button.innerHTML = 'Delete Request';
			requestContainer.appendChild(div1);
		 }
	 }
 }
 
 xhr.send(null);
 
 requestContainer.appendChild(document.createElement('P').innerHTML = 'hi');