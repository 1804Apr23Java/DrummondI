var homework = {};

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

for(int i = 0; i < 25; i++) {
	console.log('Fibonacci number ' + i + " = " + homework.fibonacci(i));
}