Ext.define('HOR.view.permisos.PanelPermisos', {
	extend : 'Ext.panel.Panel',
	title : 'Gestió de Permisos',
	requires : [ 'HOR.view.permisos.GridPermisos' ],
	alias : 'widget.panelPermisos',
	closable : true,
	layout : {
		type : 'vbox',
		align : 'stretch',
		padding : 10
	},

	tbar: [
	       { xtype: 'button', text: 'Afegir', name: 'anyadir-permiso' },
	       { xtype: 'button', text: 'Esborrar', name: 'borrar-permiso' }
	     ],

	items : [ {
		xtype : 'gridPermisos'
	} ]

});