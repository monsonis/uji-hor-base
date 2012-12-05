Ext.define('HOR.view.horarios.PanelCalendarioDetalle',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendarioDetalle',    
    region : 'center',
    title : 'Calendari detall',
    depends : [ 'HOR.store.StoreCalendarios', 'HOR.store.StoreEventosDetalle' ],
    calendarStore : Ext.create('HOR.store.StoreCalendarios'),
    eventStore : Ext.create('HOR.store.StoreEventosDetalle'),
    editModal : true,
    flex : 1,
    padding : 5,
    showMultiDayView : false,
    showMultiWeekView : false,
    showMonthView : false,
    showWeekView : false,
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

    limpiaCalendario : function()
    {
        this.store.removeAll(false);
    },
    initComponent : function()
    {
        Extensible.calendar.template.BoxLayout.override(
        {
            firstWeekDateFormat : 'D j',
            multiDayFirstDayFormat : 'M j, Y',
            multiDayMonthStartFormat : 'M j'
        });

        this.callParent(arguments);
    },
    getMultiDayText : function()
    {
        return 'Setmana';
    },

    onStoreUpdate: function() {
    }
});