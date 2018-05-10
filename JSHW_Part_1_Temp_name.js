var homework = {};

/* Returns the nth Fibonacci number or -1 if passed a value that is not a number or is less than 0.
 */
homework.fibonacci = function(n) {
	if(typeof n !== "number" ||  n < 0) return -1;
	
	let i = 0;
	let k = 1;
	for(let j = 0; j < n; j++) {
		let temp = k;
		k += i;
		i = temp;
	}
	
	return i;
}

/* TESTING
for(let i = 0; i < 25; i++) {
	console.log('Fibonacci number ' + i + " = " + homework.fibonacci(i));
}
*/


/* Sorts an array of integers from least to greatest. Returns true if sorted and false if passed
 * an unsortable array.
 * 
 */
 

homework.sort = function(array) {
	if(!array || !Array.isArray(array)) return false;
	
	for(let i = 0; i < array.length ; i++) {
		let min = array[i];
		let minInd = i;
		
		for(let j = i; j < array.length; j++) {
			if(typeof array[j] !== 'number') return false;
			
			if(array[j] < min) {
				min = array[j];
				minInd= j;
			}
		}
		let temp = array[i];
		array[i] = array[minInd];
		array[minInd] = temp;
	}
	
	return true;
}


/*
let t1 = [10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10];
console.log(t1);
console.log(homework.sort(t1));
console.log(t1);
console.log(" ");

let t2 = [-34329, 565.7, 7, 2, 0, 5, -6, -343, 0.123, 55, 9];
console.log(t2);
console.log(homework.sort(t2));
console.log(t2);
*/


/* Returns the nth factorial of a number or -1 for invalid input
 *
 */
homework.factorial = function(n) {
	if(typeof n !== 'number' || n < 0) return -1;
	
	let factorial = 1;
	for(let i = n; i > 0; i--) {
		factorial *= i;
	}
	
	return factorial;
}

/* TESTING homework.factorial
for(let i = 0; i < 10; i++) {
	console.log(i + "! = " + homework.factorial(i));
}
*/

/* PROBLEM 4 : Rotate Left
 *
 */
homework.rotateLeft = function(array, n) {
	if(!Array.isArray(array) || typeof n !== 'number' || n < 0) return false;
	
	let len = array.length;
	
	if(len == 0 || len == 1) return true;
	
	let shift = (n % len);
	
	let count = 0;
	let i = 0;
	let holdValue = array[0];
	while(count < len) {
		let j = (i - shift >= 0) ? (i - shift) : (len + i - shift);
		
		let temp = array[j];
		array[j] = holdValue;
		holdValue = temp;
		
		i = j;
		count++;
	}
	
	return true;
}

/* TESTING problem 4
let a1 = [1,2,3,4,5];
console.log(a1);
homework.rotateLeft(a1, 1);
console.log(a1);
console.log(" ");

a1 = [1,2,3,4,5];
console.log(a1);
homework.rotateLeft(a1, 6);
console.log(a1);
console.log(" ");

a1 = [1,2,3,4,5];
console.log(a1);
homework.rotateLeft(a1, 3);
console.log(a1);
console.log(" ");
*/

homework.balancedBrackets = function(bracketsString) {
	if(!typeof bracketsString === "string") return false;
	if(bracketsString.length % 2 === 1) return false;
	
	array = [];
	for(let i = 0; i < bracketsString.length; i++) {
		let c = bracketsString.charAt(i);
		switch(c) {
			case '{' : array.push(c); break;
			case '}' : if(array.pop() !== '{') return false;
					   break;
			case '[' : array.push(c); break;
			case ']' : if(array.pop() !== '[') return false;
					   break;
			case '(' : array.push(c); break;
			case ')' : if(array.pop() !== '(') return false;
					   break;
			default  : return false;
		}
	}
	
	return(array.length === 0) ? true : false;
}

/* TESTING question 5
let s1 = '()';
console.log(s1 + ' is balanced = ' + homework.balancedBrackets(s1));
console.log(" ");

s1 = '()()';
console.log(s1 + ' is balanced = ' + homework.balancedBrackets(s1));
console.log(" ");

s1 = '(())';
console.log(s1 + ' is balanced = ' + homework.balancedBrackets(s1));
console.log(" ");

s1 = '({[]})';
console.log(s1 + ' is balanced = ' + homework.balancedBrackets(s1));
console.log(" ");

s1 = '([])[{{}}[])';
console.log(s1 + ' is balanced = ' + homework.balancedBrackets(s1));
console.log(" ");

s1 = '(';
console.log(s1 + ' is balanced = ' + homework.balancedBrackets(s1));
console.log(" ");

s1 = '}';
console.log(s1 + ' is balanced = ' + homework.balancedBrackets(s1));
console.log(" ");

s1 = '(()(';
console.log(s1 + ' is balanced = ' + homework.balancedBrackets(s1));
console.log(" ");

s1 = '([)]';
console.log(s1 + ' is balanced = ' + homework.balancedBrackets(s1));
console.log(" ");
*/