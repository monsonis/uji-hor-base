Ext.define('HOR.view.horarios.PanelHorarios',
{
    extend : 'Ext.panel.Panel',
    title : 'Gestión Horarios',
    alias : 'widget.panelHorarios',
    requires : [ 'HOR.view.horarios.FiltroGrupos', 'HOR.view.horarios.PanelCalendario',
            'HOR.view.horarios.SelectorGrupos', 'HOR.view.horarios.SelectorCalendarios' ],

    closable : true,
    layout :
    {
        type : 'vbox',
        align : 'stretch',
        padding : 5
    },

    initComponent : function()
    {
        this.callParent();
    },

    items : [
    {
        xtype : 'filtroGrupos',
        height : /* 120 */140
    },
    {
        xtype : 'panel',
        flex : 1,
        border : 0,
        layout :
        {
            type : 'hbox',
            align : 'stretch'
        },
        items : [
        {
            width : 200,
            border : 0,
            layout :
            {
                type : 'vbox',
                align : 'stretch'
            },
            items : [
            {
                xtype : 'selectorGrupos',
            },
            {
                xtype : 'selectorCalendarios'
            } ]
        },
        {
            xtype : 'panelCalendario',
            flex : 1,
            itemId : 'calendarioMi',
        },
        {
            xtype : 'panelCalendario',
            flex : 1,
            itemId : 'calendarioMa',
            hidden : true,
            multiDayViewCfg :
            {
                dayCount : 5,
                startDay : 1,
                startDayIsStatic : true,
                viewStartHour : 8,
                showTime : false,
                showMonth : false,
                viewEndHour : 15,
                getStoreParams : function()
                {
                    var params = this.getStoreDateParams();
                    params.estudioId = this.store.getProxy().extraParams['estudioId'];
                    params.cursoId = this.store.getProxy().extraParams['cursoId'];
                    params.grupoId = this.store.getProxy().extraParams['grupoId'];
                    params.semestreId = this.store.getProxy().extraParams['semestreId'];
                    params.calendariosIds = this.store.getProxy().extraParams['calendariosIds'];
                    return params;
                }
            }
        },
        {
            xtype : 'panelCalendario',
            flex : 1,
            itemId : 'calendarioTa',
            hidden : true,
            multiDayViewCfg :
            {
                dayCount : 5,
                startDay : 1,
                startDayIsStatic : true,
                viewStartHour : 15,
                showTime : false,
                showMonth : false,
                viewEndHour : 22,
                getStoreParams : function()
                {
                    var params = this.getStoreDateParams();
                    params.estudioId = this.store.getProxy().extraParams['estudioId'];
                    params.cursoId = this.store.getProxy().extraParams['cursoId'];
                    params.grupoId = this.store.getProxy().extraParams['grupoId'];
                    params.semestreId = this.store.getProxy().extraParams['semestreId'];
                    params.calendariosIds = this.store.getProxy().extraParams['calendariosIds'];
                    return params;
                }
            }
        } ]
    } ]

});