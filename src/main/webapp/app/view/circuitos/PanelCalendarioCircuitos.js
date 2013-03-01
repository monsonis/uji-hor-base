Ext.define('HOR.view.circuitos.PanelCalendarioCircuitos',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendarioCircuitos',
    region : 'center',
    title : 'Circuit',
    depends : [ 'HOR.store.StoreCalendarios', 'HOR.store.StoreEventosCircuito' ],
    calendarStore : Ext.create('HOR.store.StoreCalendarios'),
    eventStore : Ext.create('HOR.store.StoreEventosCircuito'),
    editModal : true,
    flex : 1,
    padding : 5,
    showTodayText : false,
    showNavToday : false,
    showDayView : false,
    showWeekView : false,
    showMonthView : false,
    viewConfig :
    {
        viewStartHour : 8,
        viewEndHour : 22
    },
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
            params.semestreId = this.store.getProxy().extraParams['semestreId'];
            params.grupoId = this.store.getProxy().extraParams['grupoId'];
            params.circuitoId = this.store.getProxy().extraParams['circuitoId'];
            return params;
        }
    },
    getMultiDayText : function()
    {
        return 'Setmana genèrica';
    },

    limpiaCalendario : function()
    {
        this.store.removeAll(false);
        this.setTitle('Circuit');
    },

    initComponent : function()
    {
        Extensible.calendar.template.BoxLayout.override(
        {
            firstWeekDateFormat : 'l',
            multiDayFirstDayFormat : 'l',
            multiDayMonthStartFormat : 'l'
        });

        this.callParent(arguments);
    },
    onStoreUpdate : function()
    {
    },

    getEventStore : function()
    {
        return this.store;
    }
});