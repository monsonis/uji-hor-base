Ext.define('Event.form.field.DateRange',
{
    extend : 'Ext.form.FieldContainer',
    alias : 'widget.daterangefield',

    requires : [ 'Ext.form.field.Time', 'Ext.form.Label' ],

    /**
     * @cfg {String} toText The text to display in between the date/time fields (defaults to 'to')
     */
    toText : 'a',

    /**
     * @cfg {String} dateFormat The date display format used by the date fields (defaults to
     *      'n/j/Y')
     */
    dateFormat : 'n/j/Y',

    // private
    fieldLayout :
    {
        type : 'hbox',
        defaultMargins :
        {
            top : 0,
            right : 5,
            bottom : 0,
            left : 0
        }
    },

    // private
    initComponent : function()
    {
        var me = this;
        /**
         * @cfg {String} timeFormat The time display format used by the time fields. By default the
         *      DateRange uses the {@link Extensible.Date.use24HourTime} setting and sets the format
         *      to 'g:i A' for 12-hour time (e.g., 1:30 PM) or 'G:i' for 24-hour time (e.g., 13:30).
         *      This can also be overridden by a static format string if desired.
         */
        me.timeFormat = me.timeFormat || (Extensible.Date.use24HourTime ? 'G:i' : 'g:i A');

        me.addCls('ext-dt-range');

        me.layout = me.fieldLayout;
        me.items = me.getFieldConfigs();

        me.callParent(arguments);
        me.initRefs();
    },

    initRefs : function()
    {
        var me = this;
        me.startDate = me.down('#' + me.id + '-start-date');
        me.day = me.down('#' + me.id + '-day');
        me.startTime = me.down('#' + me.id + '-start-time');
        me.endTime = me.down('#' + me.id + '-end-time');
        me.endDate = me.down('#' + me.id + '-end-date');
        me.toLabel = me.down('#' + me.id + '-to-label');
    },

    getFieldConfigs : function()
    {
        var me = this;
        return [ me.getStartDateConfig(), me.getDayConfig(), me.getStartTimeConfig(), me.getDateSeparatorConfig(), me.getEndTimeConfig(), me.getEndDateConfig(), ];
    },

    getLayoutItems : function()
    {
        var me = this;
        return me.items.items;
    },

    getDayConfig : function()
    {
        return
        {
            xtype : 'combobox',
            id : this.id + '-day',
            labelWidth : 0,
            hideLabel : true,
            width : 90,
            valueField : 'index',
            displayField : 'day',
            store : Ext.create('Ext.data.ArrayStore',
            {
                fields : [ 'index', 'day' ],
                data : [ [ '1', 'Dilluns' ], [ '2', 'Dimarts' ], [ '3', 'Dimecres' ], [ '4', 'Dijous' ], [ '5', 'Divendres' ] ]
            }),
            listeners :
            {
                'select' :
                {
                    fn : function()
                    {
                        this.updateDates(parseInt(this.day.getValue()));
                    },
                    scope : this
                }
            }
        };
    },

    getStartDateConfig : function()
    {
        return
        {
            xtype : 'datefield',
            name : 'start-date-hidden',
            id : this.id + '-start-date',
            format : this.dateFormat,
            hidden : true
        };
    },

    getStartTimeConfig : function()
    {
        return
        {
            xtype : 'timefield',
            id : this.id + '-start-time',
            hidden : this.showTimes === false,
            labelWidth : 0,
            hideLabel : true,
            width : 90,
            format : this.timeFormat,
            listeners :
            {
                'select' :
                {
                    fn : function()
                    {
                        this.onFieldChange('time', 'start');
                    },
                    scope : this
                }
            }
        };
    },

    getEndDateConfig : function()
    {
        return
        {
            xtype : 'datefield',
            id : this.id + '-end-date',
            format : this.dateFormat,
            hidden : true
        };
    },

    getEndTimeConfig : function()
    {
        return
        {
            xtype : 'timefield',
            id : this.id + '-end-time',
            hidden : this.showTimes === false,
            labelWidth : 0,
            hideLabel : true,
            width : 90,
            format : this.timeFormat,
            listeners :
            {
                'select' :
                {
                    fn : function()
                    {
                        this.onFieldChange('time', 'end');
                    },
                    scope : this
                }
            }
        };
    },

    getDateSeparatorConfig : function()
    {
        return
        {
            xtype : 'label',
            id : this.id + '-to-label',
            text : this.toText,
            margins :
            {
                top : 4,
                right : 5,
                bottom : 0,
                left : 0
            },
            disabledCls : 'opacity: .3'
        };
    },

    // private
    onFieldChange : function(type, startend)
    {
        this.checkDates(type, startend);
        this.fireEvent('change', this, this.getValue());
    },

    // private
    updateDates : function(newDay)
    {
        var dt = this.getDT('start');
        var oldDay = parseInt(Ext.Date.format(dt, 'N'));

        dt = Ext.Date.add(dt, Ext.Date.DAY, newDay - oldDay);
        this.startDate.setValue(dt);
        this.endDate.setValue(dt);
    },

    // private
    checkDates : function(type, startend)
    {
        var me = this, typeCap = type === 'date' ? 'Date' : 'Time', startField = this['start' + typeCap], endField = this['end' + typeCap], startValue = me.getDT('start'), endValue = me.getDT('end');

        if (startValue > endValue)
        {
            if (startend == 'start')
            {
                endField.setValue(startValue);
            }
            else
            {
                startField.setValue(endValue);
                me.checkDates(type, 'start');
            }
        }
        if (type == 'date')
        {
            me.checkDates('time', startend);
        }
    },

    /**
     * Returns an array containing the following values in order:<div class="mdetail-params">
     * <ul>
     * <li><b><code>DateTime</code></b> : <div class="sub-desc">The start date/time</div></li>
     * <li><b><code>DateTime</code></b> : <div class="sub-desc">The end date/time</div></li>
     * <li><b><code>Boolean</code></b> : <div class="sub-desc">True if the dates are all-day,
     * false if the time values should be used</div></li>
     * <ul>
     * </div>
     * 
     * @return {Array} The array of return values
     */
    getValue : function()
    {
        return [ this.getDT('start'), this.getDT('end') ];
    },

    // private getValue helper
    getDT : function(startend)
    {
        var time = this[startend + 'Time'].getValue(), dt = this[startend + 'Date'].getValue();

        if (Ext.isDate(dt))
        {
            dt = Ext.Date.format(dt, this[startend + 'Date'].format);
        }
        else
        {
            return null;
        }
        ;
        if (time && time != '')
        {
            time = Ext.Date.format(time, this[startend + 'Time'].format);
            var val = Ext.Date.parseDate(dt + ' ' + time, this[startend + 'Date'].format + ' ' + this[startend + 'Time'].format);
            return val;
            // return Ext.Date.parseDate(dt+' '+time, this[startend+'Date'].format+'
            // '+this[startend+'Time'].format);
        }
        return Ext.Date.parseDate(dt, this[startend + 'Date'].format);

    },

    /**
     * Sets the values to use in the date range.
     * 
     * @param {Array/Date/Object}
     *            v The value(s) to set into the field. Valid types are as follows:<div
     *            class="mdetail-params">
     *            <ul>
     *            <li><b><code>Array</code></b> : <div class="sub-desc">An array containing, in
     *            order, a start date, end date and all-day flag. This array should exactly match
     *            the return type as specified by {@link #getValue}.</div></li>
     *            <li><b><code>DateTime</code></b> : <div class="sub-desc">A single Date
     *            object, which will be used for both the start and end dates in the range. The
     *            all-day flag will be defaulted to false.</div></li>
     *            <li><b><code>Object</code></b> : <div class="sub-desc">An object containing
     *            properties for StartDate, EndDate and IsAllDay as defined in
     *            {@link Extensible.calendar.data.EventMappings}.</div></li>
     *            <ul>
     *            </div>
     */
    setValue : function(v)
    {
        if (!v)
        {
            return;
        }
        var me = this, eventMappings = Extensible.calendar.data.EventMappings, startDateName = eventMappings.StartDate.name;

        if (Ext.isArray(v))
        {
            me.setDT(v[0], 'start');
            me.setDT(v[1], 'end');
            me.day.setValue(Ext.Date.format(v[0], 'N'));
        }
        else if (Ext.isDate(v))
        {
            me.setDT(v, 'start');
            me.setDT(v, 'end');
            me.day.setValue(Ext.Date.format(v, 'N'));
        }
        else if (v[startDateName])
        { // object
            me.setDT(v[startDateName], 'start');
            if (!me.setDT(v[eventMappings.EndDate.name], 'end'))
            {
                me.setDT(v[startDateName], 'end');
            }
            me.day.setValue(Ext.Date.format(v[startDateName], 'N'));
        }
    },

    // private setValue helper
    setDT : function(dt, startend)
    {
        if (dt && Ext.isDate(dt))
        {
            this[startend + 'Date'].setValue(dt);
            this[startend + 'Time'].setValue(Ext.Date.format(dt, this[startend + 'Time'].format));
            return true;
        }
    },

    // inherited docs
    isDirty : function()
    {
        var dirty = false;
        if (this.rendered && !this.disabled)
        {
            this.items.each(function(item)
            {
                if (item.isDirty())
                {
                    dirty = true;
                    return false;
                }
            });
        }
        return dirty;
    },

    // inherited docs
    reset : function()
    {
        this.delegateFn('reset');
    },

    // private
    delegateFn : function(fn)
    {
        this.items.each(function(item)
        {
            if (item[fn])
            {
                item[fn]();
            }
        });
    },

    // private
    beforeDestroy : function()
    {
        Ext.destroy(this.fieldCt);
        this.callParent(arguments);
    },

    /**
     * @method getRawValue
     * @hide
     */
    getRawValue : Ext.emptyFn,
    /**
     * @method setRawValue
     * @hide
     */
    setRawValue : Ext.emptyFn,

    disableFields : function()
    {
        this.day.setReadOnly(true);
    },

    enableFields : function()
    {
        this.day.setReadOnly(false);
    }
});
