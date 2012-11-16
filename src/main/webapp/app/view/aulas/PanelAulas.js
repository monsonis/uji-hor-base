Ext.define('HOR.view.aulas.PanelAulas', {
	extend : 'Ext.panel.Panel',
	title : 'Assignació d\'aules a estudis',
	requires : ['HOR.view.aulas.FiltroAsignacionAulas'],
	alias : 'widget.panelAulas',
	closable : true,
	layout : {
		type : 'vbox',
		align : 'center',		
		padding : 10
	},


	items : [ {
		xtype : 'filtroAsignacionAulas',
		width: 800,		
	} ]

});