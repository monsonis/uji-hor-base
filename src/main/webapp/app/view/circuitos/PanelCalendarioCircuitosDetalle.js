Ext.define('HOR.view.circuitos.PanelCalendarioCircuitosDetalle',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendarioCircuitosDetalle',
    region : 'center',
    title : 'Circuit',
    depends : [ 'HOR.store.StoreCalendarios', 'HOR.store.StoreAulasGenerica' ],
    calendarStore : Ext.create('HOR.store.StoreCalendarios'),
    eventStore : Ext.create('HOR.store.StoreAulasGenerica'),
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
            params.aulaId = this.store.getProxy().extraParams['aulaId'];
            params.semestreId = this.store.getProxy().extraParams['semestreId'];
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
    
    getEventStore : function() {
        return this.store;
    }
});