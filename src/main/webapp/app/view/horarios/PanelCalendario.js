Ext.define('HOR.view.horarios.PanelCalendario',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendario',
    region : 'center',
    title : 'Calendari',
    requires : [ 'HOR.store.StoreEventos', 'HOR.store.StoreCalendarios' ],
    editModal : true,
    flex : 1,
    padding : 5,
    weekViewCfg :
    {
        dayCount : 5,
        startDay : 1,
        startDayIsStatic : true,
        viewStartHour : 8,
        viewEndHour : 22,
    // hourHeight: 84
    },
    dayViewCfg :
    {
        viewStartHour : 8,
        viewEndHour : 22
    },
    showMultiDayView : true,
    showMultiWeekView : false,
    multiDayViewCfg :
    {
        dayCount : 5,
        viewStartHour : 8,
        viewEndHour : 22
    },
    getMultiDayText : function()
    {
        return 'Setmana genèrica';
    },
    initComponent : function()
    {
        var ref = this;
        var calendarStore = Ext.create('HOR.store.StoreCalendarios');
        var eventStore = Ext.create('HOR.store.StoreEventos');

        Ext.apply(ref, {
            eventStore: eventStore,
            calendarStore: calendarStore
        });

        this.callParent(arguments);

    }
});