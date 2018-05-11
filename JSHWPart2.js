//Part II will focus on Javascript's ability to manipulate the DOM.
//Use the provided HTML page.
//Put your Javascript in the provided <script> element at the bottom of the page.
//Please put the question itself as a comment above each answer.
//Due 5pm, Monday, May 14 in your branch. 





//1. USA
//Define function getUSA()
//Find the html element that contains "USA".
//Print that element's contents.
  

  
  
  
  
  
//2. Sales
//Define function getPeopleInSales()
//Print the names of all the people in the sales department.
function getPeopleInSales() {
	let array = document.getElementsByClassName('empName');
	
	for(let i = 0; i < array.length; i++) {
		let sibling = array[i].nextElementSibling;
		if(sibling && sibling.innerHTML === 'Sales') {
			console.log(array[i].innerHTML);
		}
	}
}
//getPeopleInSales();






//3. Click Here
//Define function getAnchorChildren()
//Find all anchor elements with a <span> child.
//Print the contents of <span>
function getAnchorChildren() {
	let array = document.getElementsByTagName('A');
	
	for(let i = 0; i < array.length; i++) {
		
		let child = array[i].firstElementChild;
		
		while(child) {
			if(child.tagName === 'SPAN') {
				console.log(child.innerHTML);
				break;
			}
			child = child.nextElementSibling;
		}
	}
}
//getAnchorChildren();





//4. Hobbies
//Define function     
//Find all checked options in the 'skills' select element.
//Print the value and the contents.
function getHobbies() {
	let array = document.getElementsByName('skills');
	
	//Expect only one skills section
	let skillSelect = array[0];
	if(skillSelect && skillSelect.tagName === 'SELECT') {
		let child = skillSelect.firstElementChild;
		
		while(child && child.tagName === 'OPTION') {
			console.log('value = ' + child.getAttribute('value') + ", contents = " + child.innerHTML);
			child = child.nextElementSibling;
		}
	}
	
}
//getHobbies();






//5. Custom Attribute
//Define function getCustomAttribute()
//Find all elements with "data-customAttr" attribute
//Print the value of the attribute.
//Print the element that has the attribute.
function getCustomerAttribute() {
	let array = document.querySelectorAll('[data-customAttr]');
	
	for(let i = 0 ; i < array.length; i++) {
		console.log('value = ' + array[i].getAttribute('data-customAttr') + ', element = ' + array[i].tagName);
	}
}
//getCustomerAttribute();





//6. Sum Event
//NOTE: Write unobtrusive Javascript
//Regarding these elements:
//<input id="num1" class="nums" type="text"/>
//<input id="num2" class="nums" type="text"/>
//<h3>Sum: span id="sum"></span></h3>
//Define onchange event handler.
//Add <input> element values.
//Put the sum in the <span> element.
//If values cannot be added, put "Cannot add" in the <span> element
let num1 = document.getElementById('num1');
let num2 = document.getElementById('num2');
let sum = document.getElementById('sum');

let sumEvent = function() {
	let n1 = new Number(num1.value);
	let n2 = new Number(num2.value);
	n1 = n1 + n2;
	
	if(n1) {
		sum.innerHTML = String(n1);
	} else {
		sum.innerHTML = 'Cannot add';
	}
}

num1.addEventListener('change', sumEvent);
num2.addEventListener('change', sumEvent);









//7. Skills Event
//NOTE: Write unobtrusive Javascript
//When user selects a skill, create an alert with a message similar to:
//"Are you sure CSS is one of your skills?"
//NOTE: no alert should appear when user deselects a skill.
let skillSelect = document.getElementsByName('skills')[0].addEventListener('change', function(e) {
	let val = e.target.value;
	let valElement = document.querySelectorAll('option[value="' + val + '"]')[0];
	
	alert('Are you sure ' + valElement.innerHTML + ' is one of your skills?');
})






//8. Favorite Color Event
//NOTE: Write unobtrusive Javascript
//NOTE: This is regarding the favoriteColor radio buttons.
//When a user selects a color, create an alert with a message similar to:
//"So you like green more than blue now?"
//In this example, green is the new value and blue is the old value.
//Make the background color (of all favoriteColor radio buttons) 
//the newly selected favoriteColor


//9. Show/Hide Event
//NOTE: Write unobtrusive Javascript
//When user hovers over an employees name:
//Hide the name if shown.
//   Show the name if hidden.


//10. Current Time
//Regarding this element:
//    <h5 id="currentTime"></h5>
//Show the current time in this element in this format: 9:05:23 AM
//The time should be accurate to the second without having to reload the page.


//11. Delay
//Regarding this element:
//    s
//<p id="helloWorld">Hello, World!</p>
//Three seconds after a user clicks on this element, change the text to a random color.


//12. Walk the DOM
//Define function walkTheDOM(node, func)
//This function should traverse every node in the DOM. 
//Use recursion.
//On each node, call func(node).