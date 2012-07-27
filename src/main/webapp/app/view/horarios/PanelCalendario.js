Ext.define('HOR.view.horarios.PanelCalendario',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendario',
    region : 'center',
    title : 'Calendari',
    depends : [ 'HOR.store.StoreCalendarios', 'HOR.store.StoreEventos' ],
    calendarStore : Ext.create('HOR.store.StoreCalendarios'),
    eventStore : Ext.create('HOR.store.StoreEventos'),
    editModal : true,
    flex : 1,
    padding : 5,
    activeItem : 1,
    showTodayText : false,
    showNavToday : false,
    showDayView : false,
    showWeekView : false,
    showMonthView : false,
    weekViewCfg :
    {
        dayCount : 5,
        startDay : 1,
        startDayIsStatic : true,
    },
    showMultiDayView : true,
    showMultiWeekView : false,
    showNavJump : false,
    showNavNextPrev : false,
    multiDayViewCfg :
    {
        dayCount : 5,
        startDay : 1,
        startDayIsStatic : true,
        showTime : false,
        showMonth : false,
        getStoreParams : function()
        {
            var params = this.getStoreDateParams();
            params.estudioId = this.store.getProxy().extraParams['estudioId'];
            params.cursoId = this.store.getProxy().extraParams['cursoId'];
            params.grupoId = this.store.getProxy().extraParams['grupoId'];
            params.semestreId = this.store.getProxy().extraParams['semestreId'];
            return params;
        }
    },
    getMultiDayText : function()
    {
        return 'Setmana genèrica';
    },

    initComponent : function()
    {
        this.callParent(arguments);
    },

    limpiaCalendario : function()
    {
        this.store.removeAll(false);
    }
});

Extensible.calendar.menu.Event.override(
{
    buildMenu : function()
    {
        var me = this;

        if (me.rendered)
        {
            return;
        }
        Ext.apply(me,
        {
            items : [
            {
                text : me.editDetailsText,
                iconCls : 'extensible-cal-icon-evt-edit',
                menu : me.dateMenu
            },
            {
                text : 'Assignar aula',
                iconCls : 'extensible-cal-icon-evt-edit',
                menu : me.dateMenu

            },
            {
                text : 'Assignar a circuit',
                iconCls : 'extensible-cal-icon-evt-edit',
                menu : me.copyMenu

            }, '-',
            {
                text : 'Dividir',
                iconCls : 'extensible-cal-icon-evt-copy',
                scope : me,
                handler : function()
                {
                    Ext.ComponentQuery.query("panelCalendario")[0].fireEvent('eventdivide', me, me.rec);
                }
            },
            {
                text : me.deleteText,
                iconCls : 'extensible-cal-icon-evt-del',
                scope : me,
                handler : function()
                {
                    me.fireEvent('eventdelete', me, me.rec, me.ctxEl);
                }
            } ]
        });
    },

    showForEvent : function(rec, el, xy)
    {
        var me = this;
        me.rec = rec;
        me.ctxEl = el;
        me.showAt(xy);
    }
});

Extensible.calendar.template.BoxLayout.override(
{
    firstWeekDateFormat : 'l',
    multiDayFirstDayFormat : 'l'
});