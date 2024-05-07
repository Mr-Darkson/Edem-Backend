/*
 * ATTENTION: The "eval" devtool has been used (maybe by default in mode: "development").
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
/******/ (function() { // webpackBootstrap
/******/ 	"use strict";
/******/ 	var __webpack_modules__ = ({

/***/ "./src/js/main.js":
/*!************************!*\
  !*** ./src/js/main.js ***!
  \************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _module_sum_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./module/sum.js */ \"./src/js/module/sum.js\");\n\r\nconsole.log((0,_module_sum_js__WEBPACK_IMPORTED_MODULE_0__[\"default\"])(2, 10));\r\nconsole.log((0,_module_sum_js__WEBPACK_IMPORTED_MODULE_0__[\"default\"])(12, 5));\r\n\r\ndocument.querySelector('.readmeorno-span').addEventListener('click', function() {\r\n    this.classList.toggle('unread');\r\n    this.classList.toggle('read');\r\n});\r\n\r\ndocument.getElementById('include-date').innerText = new Date().toLocaleDateString();\r\n\r\n\r\ndocument.addEventListener(\"DOMContentLoaded\", function() {\r\n    var daySelect = document.getElementById(\"daySelect\");\r\n    var monthSelect = document.getElementById(\"monthSelect\");\r\n    var yearSelect = document.getElementById(\"yearSelect\");\r\n\r\n    monthSelect.addEventListener(\"change\", function() {\r\n        updateDaysInMonth();\r\n    });\r\n\r\n    updateDaysInMonth();\r\n\r\n    function updateDaysInMonth() {\r\n        var daysInMonth = getDaysInMonth(parseInt(monthSelect.value), parseInt(yearSelect.value));\r\n        \r\n        while (daySelect.options.length > 0) {\r\n            daySelect.remove(0);\r\n        }\r\n\r\n        for (var i = 1; i <= daysInMonth; i++) {\r\n            var option = document.createElement(\"option\");\r\n            option.text = i;\r\n            daySelect.add(option);\r\n        }\r\n    }\r\n\r\n    function getDaysInMonth(month, year) {\r\n        return new Date(year, month, 0).getDate();\r\n    }\r\n});\r\n\r\nfunction submitBirthday() {\r\n    var day = document.getElementById(\"daySelect\").value;\r\n    var month = document.getElementById(\"monthSelect\").value;\r\n    var year = document.getElementById(\"yearSelect\").value;\r\n    var birthday = year + \"-\" + month + \"-\" + day;\r\n    \r\n    document.getElementById(\"birthdayInput\").value = birthday;\r\n}\n\n//# sourceURL=webpack://gulp-starter/./src/js/main.js?");

/***/ }),

/***/ "./src/js/module/sum.js":
/*!******************************!*\
  !*** ./src/js/module/sum.js ***!
  \******************************/
/***/ (function(__unused_webpack_module, __webpack_exports__, __webpack_require__) {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony default export */ __webpack_exports__[\"default\"] = ((a, b) => a + b);\r\n\r\n\n\n//# sourceURL=webpack://gulp-starter/./src/js/module/sum.js?");

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	!function() {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = function(exports) {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	}();
/******/ 	
/************************************************************************/
/******/ 	
/******/ 	// startup
/******/ 	// Load entry module and return exports
/******/ 	// This entry module can't be inlined because the eval devtool is used.
/******/ 	var __webpack_exports__ = __webpack_require__("./src/js/main.js");
/******/ 	
/******/ })()
;