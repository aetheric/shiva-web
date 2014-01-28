<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html>
	<head>
		<title>${applicationScope.appname}</title>

		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

		<jwr:style src="/bundles/lib.css" media="screen"/>
		<jwr:style src="/bundles/app.css" media="screen"/>
		<jwr:script src="/bundles/lib.js"/>
		<jwr:script src="/bundles/app.js"/>
	</head>
	<body ng-module="app">
		<div ng-view></div>
	</body>
</html>