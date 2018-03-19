angular.module("emailparser", [])
// .config(["$interpolateProvider", function($interpolateProvider) {
// $interpolateProvider.startSymbol("*");
// $interpolateProvider.endSymbol("*");
// }])
.factory("Emailparser", [ "$interpolate", function($interpolate) {
	return {
		parse : function(text, context) {
			var template = $interpolate(text);
			return template(context);
		}
	};
} ]);
angular.module("filters", []).filter("firUppercase", function() {
	return function(input) {
		if (input) {
			return input[0].toUpperCase() + input.slice(1);
		}
	};
});

var app = angular.module("app", [ "emailparser", "filters" ]);
app.controller("oController", function($scope, $parse) {
	$scope.$watch("expr", function(newV, oldV, scope) {
		if (newV !== oldV) {
			var parseFun = $parse(newV);
			$scope.parseExpr = parseFun(scope);
		}
	});
});

app.controller("firController", function($scope, $timeout) {
	$scope.clock = {
		now : new Date()
	};
	var upclock = function() {
		$scope.clock.now = new Date();
	};
	setInterval(function() {
		$scope.$apply(upclock);
	}, 1000);
	upclock();
});

app.controller("secController", function($scope) {
	$scope.counter = 0;
	$scope.add = function(num) {
		$scope.counter += num;
	};
	$scope.subtract = function(num) {
		$scope.counter -= num;
	};
});

app.controller("thiController", [ "$scope", "Emailparser",
		function($scope, Emailparser) {
			$scope.$watch("emailBody", function(body) {
				if (body) {
					$scope.parseVal = Emailparser.parse(body, {
						to : $scope.to
					});
				}
			});
		} ]);

app.controller("forController", function($scope) {
	$scope.number = {
		num1 : 123,
		num2 : new Date()
	};
});

app.controller("fivController", [ "$scope", function($scope) {
	$scope.signup = {
		name : "",
		email : ""
	};
	$scope.submitted = false;
	$scope.submitForm = function() {
		if ($scope.sign_up.$valid) {
			alert("success");
		} else {
			$scope.sign_up.submitted = true;
		}
	};
} ]);

app.directive("ensureUnique", [ "$http", function($http) {
	return {
		require : "ngModel",
		link : function(scope, ele, attrs, c) {
			scope.$watch(attrs.ngModel, function(n) {
				if (!n)
					return;
				$http({
					method : "post",
					url : 'checkname.htm',
					data : {
						name : scope.ngModel,
						field : attrs.ensureUnique,
						value : scope.ngModel
					}
				}).success(function(data) {
					c.$setValidity("unique", data.isUnique);
				}).error(function(data) {
					c.$setValidity("unique", false);
				});
			});
		}
	};
} ]);

app.directive("myDirective", function() {
	return {
		restrict : "A",
		// replace : true,
		scope : {
			myUrl : '=someAttr',
			myLinkText : '@'
		},
		template : '<input type="text" ng-model="myUrl">	<a href="{{myUrl}}">{{myLinkText}}</a>'
	};
});

app.run(function($rootScope, $timeout) {
	$rootScope.timeShow = true;
	$timeout(function(){
		$rootScope.timeShow = false;
	}, 5000);
});

app.controller("sixController", function($scope) {
	$scope.model = {
		aValue : "hi, parent"
	};
	$scope.aFun = function() {
		$scope.model.aValue = "hi, parent, click by parent!";
	};
});
app.controller("sixChildController", function($scope) {
//	$scope.aValue = "hi, child";
	$scope.bFun = function() {
		$scope.model.aValue = "hi, child, click by child!";
	};
});