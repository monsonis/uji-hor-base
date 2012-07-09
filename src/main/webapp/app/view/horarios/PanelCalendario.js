Ext.define('HOR.view.horarios.PanelCalendario',
{
    extend : 'Extensible.calendar.CalendarPanel',
    alias : 'widget.panelCalendario',
    region : 'center',
    title : 'Calendari',
    depends: [ 'HOR.store.StoreCalendarios', 'HOR.store.StoreEventos'],
    calendarStore : Ext.create('HOR.store.StoreCalendarios'),
    eventStore: Ext.create('HOR.store.StoreEventos'),

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
        this.callParent(arguments);

    }
});