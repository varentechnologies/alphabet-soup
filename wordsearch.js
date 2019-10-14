var fs = require('fs');

var args = process.argv.slice(2, 3);

if (args.length < 1) {
	console.log("Incorrect number of arguements");
	console.log("Usage: node wordsearch.js </path/to/search/file>");
	process.exit(9);
}

var filename = args[0];

// parses the file
fs.readFile(filename, function(err, data) {
	if (err === null) {
		// get an array of all lines (splitting on line breaks)
		var lines = data.toString().split(/(?:\r\n|\r|\n)/g);

		// the first line is the dimensions of the grid
		var dimensions = lines[0].split(/\D/g).map(function (x) { return parseInt(x); });

		// based off the dimensions, read the lines for the grid
		// split each line on whitespace so each row is an array of columns
		var grid = lines.slice(1, dimensions[0] + 1).map(function (x) { return x.split(/\s/g); });

		// the remaining lines are the list of words
		var words = lines.slice(dimensions[0] + 1).map(function(x) { return x.replace(/ /g, ''); });

		// for each word, perform the search and record the answer
		var answer = [];
		words.forEach(function (word) {
			if (word.length === 0) {
				return;
			}

			var ans = search(word, grid, dimensions);

			if (ans.length !== 0) {
				answer.push([word, ans[0], ans[1]]);
			}
		});
		printAnswer(answer);
	} else {
		console.log(err);
	}
});

/*
 * search the grid for the first letter of the word
 * once you find it, 
 * 	build all possible paths that could contain that word, starting from where you find the first letter
 *	progress through those paths checking for valid answers to the search
 */
var search = function (word, gridArr, dimensionArr) {
	var answer = [];

	var firstChar = word.charAt(0).toUpperCase();
	for (var i = 0; i < gridArr.length; i++) {
		var row = gridArr[i];
		for (var j = 0; j < row.length; j++){
			var letter = row[j].toUpperCase();
			if (letter === firstChar) {
				var start = [i, j];
				paths = buildPaths(start, dimensionArr, word.length);

				paths.forEach(function (path) {
					var valid = true;
					for (var pathIndex = 0; pathIndex < path.length; pathIndex++) {
						var coord = path[pathIndex];

						var wordChar = word.charAt(pathIndex + 1).toUpperCase();
						var gridChar = gridArr[coord[0]][coord[1]].toUpperCase();

						if (wordChar !== gridChar) {
							valid = false;
							break;
						}
					}

					if (valid) {
						answer = [start, path[path.length - 1]];
					}
				});
			}
		}
	}

	return answer;
};

// builds all possible word paths from a starting point
var buildPaths = function (start, dim, len) {
	var paths = [];

	// determine if there is enough room for the remainder of the word going in a given direction
	var up = start[0] >= len - 1;
	var down = start[0] + len <= dim[0];
	var left = start[1] >= len - 1;
	var right = start[1] + len <= dim[1];

	// based on which directions are possible, create the paths that could contain the word
	if (up && left) {
		var path = [];
		for (var i = 1; i < len; i++) {
			path.push([start[0] - i, start[1] - i]);
		}
		paths.push(path);
	}
	if (up) {
		var path = [];
		for (var i = 1; i < len; i++) {
			path.push([start[0] - i, start[1]]);
		}
		paths.push(path);
	}
	if (up && right) {
		var path = [];
		for (var i = 1; i < len; i++) {
			path.push([start[0] - i, start[1] + i]);
		}
		paths.push(path);
	}
	if (right) {
		var path = [];
		for (var i = 1; i < len; i++) {
			path.push([start[0], start[1] + i]);
		}
		paths.push(path);
	}
	if (down && right) {
		var path = [];
		for (var i = 1; i < len; i++) {
			path.push([start[0] + i, start[1] + i]);
		}
		paths.push(path);
	}
	if (down) {
		var path = [];
		for (var i = 1; i < len; i++) {
			path.push([start[0] + i, start[1]]);
		}
		paths.push(path);
	}
	if (down && left) {
		var path = [];
		for (var i = 1; i < len; i++) {
			path.push([start[0] + i, start[1] - i]);
		}
		paths.push(path);
	}
	if (left) {
		var path = [];
		for (var i = 1; i < len; i++) {
			path.push([start[0], start[1] - i]);
		}
		paths.push(path);
	}

	return paths;
};

// extracts the data from the search answers and prints it in a readable format
var printAnswer = function (ans) {
	ans.forEach(function (a) {
		var w = a[0];
		var start = (a.length > 0 ? a[1][0] + ":" + a[1][1] : "not ");
		var end = (a.length > 0 ? a[2][0] + ":" + a[2][1] : "found");

		console.log(w, start, end);
	});
};
