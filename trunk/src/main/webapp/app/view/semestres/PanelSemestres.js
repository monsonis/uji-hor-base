Ext.define('HOR.view.semestres.PanelSemestres', {
	extend : 'Ext.panel.Panel',
	title : 'Dates curs acadèmic',
	requires : [ 'HOR.view.semestres.GridSemestres' ],
	alias : 'widget.panelSemestres',
	closable : true,
	layout : {
		type : 'vbox',
		align : 'center',
		padding : 10
	},

	items : [ {
		xtype : 'gridSemestres',
		width : 650,
	} ]

});