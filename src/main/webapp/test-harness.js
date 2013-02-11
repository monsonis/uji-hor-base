var Harness = Siesta.Harness.Browser.ExtJS;

Harness.configure({
	title : 'Awesome Test Suite',
	loaderPath : {
		'HOR' : 'app'
	},

	preload : [ '/hor/sinon/sinon-1.5.0.js', '/hor/sinon/sinon-init.js',
			'/hor/Ext/ext-all-debug.css',
			'/hor/extensible-1.5.1/resources/css/extensible-all.css',

			'/hor/css/custom.css', '/hor/Ext/ext-all-debug-w-comments.js',
			'/hor/Ext/ext-lang-ca.js',
			'/hor/extensible-1.5.1/lib/extensible-all-debug.js',
			'/hor/extensible-1.5.1/src/locale/extensible-lang-ca.js' ]
});

Harness.start({
	group : "Dates Curs Academic",
	items : [ 'tests/010-DatesCursAcademic/010_sanity.t.js',
			'tests/010-DatesCursAcademic/020_semestredetallemodel.t.js',
			'tests/010-DatesCursAcademic/030_panel_semestre.t.js' ]
}, {
	group : "Asignacio d'aules a estudis",
	items : [ 'tests/020-AsignacioAulesEstudis/010_sanity.t.js'
	// 'tests/020-AsignacioAulesEstudis/020_view_filtroasignacionaulas.t.js',
	]
}, {
	group : 'Application',

	// need to set the `preload` to empty array - to avoid the double loading of
	// dependencies
	preload : [],

	items : [ {
		hostPageUrl : 'index.jsp',
		url : 'tests/900-Aplicacion/900-010_app.t.js'
	}, {
		hostPageUrl : 'index.jsp',
		url : 'tests/900-Aplicacion/900-020_assial_combos.t.js'
	}, {
		hostPageUrl : 'index.jsp',
		url : 'tests/900-Aplicacion/900-030_assial_add.t.js'
	}, {
		hostPageUrl : 'index.jsp',
		url : 'tests/900-Aplicacion/900-040_assial_delete.t.js'
	} ]
}

);