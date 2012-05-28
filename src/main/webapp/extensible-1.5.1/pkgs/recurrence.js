/*
 * Extensible 1.5.1
 * Copyright(c) 2010-2012 Extensible, LLC
 * licensing@ext.ensible.com
 * http://ext.ensible.com
 */
Ext.define("Extensible.form.recurrence.AbstractOption",{extend:"Ext.form.FieldContainer",mixins:{field:"Ext.form.field.Field"},layout:"hbox",defaults:{margins:"0 5 0 0"},key:undefined,dateValueFormat:"Ymd\\THis\\Z",optionDelimiter:";",initComponent:function(){var a=this;a.addEvents("change");a.startDate=a.startDate||new Date();a.items=a.getItemConfigs();a.callParent(arguments);a.initRefs();a.initField()},formatDate:function(a){return Ext.Date.format(a,this.dateValueFormat)},parseDate:function(d,b){b=b||{};try{var a=Ext.Date.parse(d,b.format||this.dateValueFormat,b.strict);if(a){return a}}catch(c){}return b.defaultValue||new Date()},afterRender:function(){this.callParent(arguments);this.updateLabel()},initRefs:Ext.emptyFn,setStartDate:function(a){this.startDate=a;return this},getStartDate:function(){return this.startDate||Extensible.Date.today()},getDefaultValue:function(){return""},preSetValue:function(b,a){var c=this;if(!b){b=c.getDefaultValue()}if(!a){c.on("afterrender",function(){c.setValue(b)},c,{single:true});return false}c.value=b;return true}});Ext.define("Extensible.form.recurrence.option.Duration",{extend:"Extensible.form.recurrence.AbstractOption",alias:"widget.extensible.recurrence-duration",requires:["Ext.form.Label","Ext.form.field.ComboBox","Ext.form.field.Number","Ext.form.field.Date"],minOccurrences:1,maxOccurrences:999,defaultEndDateOffset:5,minDateOffset:1,maxEndDate:new Date("12/31/9999"),endDateWidth:120,cls:"extensible-recur-duration",getItemConfigs:function(){var b=this,a=b.getStartDate();return[{xtype:"label",text:"and continuing"},{xtype:"combo",itemId:b.id+"-duration-combo",mode:"local",width:85,triggerAction:"all",forceSelection:true,value:"forever",store:["forever","for","until"],listeners:{change:Ext.bind(b.onComboChange,b)}},{xtype:"datefield",itemId:b.id+"-duration-date",showToday:false,width:b.endDateWidth,format:b.endDateFormat||Ext.form.field.Date.prototype.format,maxValue:b.maxEndDate,allowBlank:false,hidden:true,minValue:Ext.Date.add(a,Ext.Date.DAY,b.minDateOffset),value:Ext.Date.add(a,Ext.Date.DAY,b.defaultEndDateOffset),listeners:{change:Ext.bind(b.onEndDateChange,b)}},{xtype:"numberfield",itemId:b.id+"-duration-num",value:5,width:55,minValue:b.minOccurrences,maxValue:b.maxOccurrences,allowBlank:false,hidden:true,listeners:{change:Ext.bind(b.onOccurrenceCountChange,b)}},{xtype:"label",itemId:b.id+"-duration-num-label",text:"occurrences",hidden:true}]},initRefs:function(){var a=this;a.untilCombo=a.down("#"+a.id+"-duration-combo");a.untilDateField=a.down("#"+a.id+"-duration-date");a.untilNumberField=a.down("#"+a.id+"-duration-num");a.untilNumberLabel=a.down("#"+a.id+"-duration-num-label")},onComboChange:function(b,a){this.toggleFields(a);this.checkChange()},toggleFields:function(a){var b=this;b.untilCombo.setValue(a);if(a==="until"){if(!b.untilDateField.getValue()){b.initUntilDate()}b.untilDateField.show()}else{b.untilDateField.hide()}if(a==="for"){b.untilNumberField.show();b.untilNumberLabel.show()}else{b.untilNumberField.hide();b.untilNumberLabel.hide()}},onOccurrenceCountChange:function(c,b,a){this.checkChange()},onEndDateChange:function(c,b,a){this.checkChange()},setStartDate:function(b){var a=this,c=a.getValue();if(b.getTime()!==a.startDate.getTime()){a.callParent(arguments);a.untilDateField.setMinValue(b);if(!c||a.untilDateField.getValue()<b){a.untilDateField.setValue(Ext.Date.add(b,Ext.Date.DAY,a.defaultEndDateOffset))}}return a},getValue:function(){var a=this;if(a.untilCombo){if(a.untilNumberField.isVisible()){return"COUNT="+a.untilNumberField.getValue()}else{if(a.untilDateField.isVisible()){return"UNTIL="+a.formatDate(this.adjustUntilDateValue(a.untilDateField.getValue()))}}}return""},adjustUntilDateValue:function(a){return Extensible.Date.add(a,{days:1,seconds:-1})},setValue:function(a){var c=this;if(!c.preSetValue(a,c.untilCombo)){return c}if(!a){c.toggleFields("forever");return c}var b=Ext.isArray(a)?a:a.split(c.optionDelimiter),e=false,d;Ext.each(b,function(f){d=f.split("=");if(d[0]==="COUNT"){c.untilNumberField.setValue(d[1]);c.toggleFields("for");e=true;return}else{if(d[0]==="UNTIL"){c.untilDateField.setValue(c.parseDate(d[1],{format:"c",defaultValue:Ext.Date.add(c.getStartDate(),Ext.Date.DAY,c.defaultEndDateOffset)}));c.toggleFields("until");e=true;return}}},c);if(!e){c.toggleFields("forever")}return c}});Ext.define("Extensible.form.recurrence.option.Interval",{extend:"Extensible.form.recurrence.AbstractOption",alias:"widget.extensible.recurrence-interval",dateLabelFormat:"l, F j",unit:"day",minValue:1,maxValue:999,cls:"extensible-recur-interval",getItemConfigs:function(){var a=this;return[{xtype:"label",text:"Repeat every"},{xtype:"numberfield",itemId:a.id+"-interval",value:1,width:55,minValue:a.minValue,maxValue:a.maxValue,allowBlank:false,enableKeyEvents:true,listeners:{change:Ext.bind(a.onIntervalChange,a)}},{xtype:"label",itemId:a.id+"-date-label"}]},initRefs:function(){var a=this;a.intervalField=a.down("#"+a.id+"-interval");a.dateLabel=a.down("#"+a.id+"-date-label")},onIntervalChange:function(c,b,a){this.checkChange();this.updateLabel()},getValue:function(){if(this.intervalField){return"INTERVAL="+this.intervalField.getValue()}return""},setValue:function(a){var c=this;if(!c.preSetValue(a,c.intervalField)){return c}if(!a){c.intervalField.setValue(c.minValue);return c}var b=Ext.isArray(a)?a:a.split(c.optionDelimiter),d;Ext.each(b,function(e){d=e.split("=");if(d[0]==="INTERVAL"){c.intervalField.setValue(d[1]);c.updateLabel();return}},c);return c},setStartDate:function(a){this.startDate=a;this.updateLabel();return this},setUnit:function(a){this.unit=a;this.updateLabel();return this},updateLabel:function(c){var b=this;if(b.intervalField){var a=b.intervalField.getValue()===1?"":"s";b.unit=c?c.toLowerCase():b.unit||"day";if(b.dateLabel){b.dateLabel.update(b.unit+a+" beginning "+Ext.Date.format(b.getStartDate(),b.dateLabelFormat))}}return b}});Ext.define("Extensible.form.recurrence.option.Monthly",{extend:"Extensible.form.recurrence.AbstractOption",alias:"widget.extensible.recurrence-monthly",requires:["Ext.form.field.ComboBox","Extensible.lang.Number"],cls:"extensible-recur-monthly",nthComboWidth:150,unit:"month",afterRender:function(){this.callParent(arguments);this.isYearly=(this.unit==="year");this.initNthCombo()},getItemConfigs:function(){return[{xtype:"label",text:"on the"},{xtype:"combobox",itemId:this.id+"-nth-combo",queryMode:"local",width:this.nthComboWidth,triggerAction:"all",forceSelection:true,displayField:"text",valueField:"value",store:Ext.create("Ext.data.ArrayStore",{fields:["text","value"],idIndex:0,data:[]}),listeners:{change:Ext.bind(this.onComboChange,this)}},{xtype:"label",text:"of each "+this.unit}]},initRefs:function(){this.nthCombo=this.down("#"+this.id+"-nth-combo")},onComboChange:function(b,a){this.checkChange()},setStartDate:function(a){if(a.getTime()!==this.startDate.getTime()){this.callParent(arguments);this.initNthCombo()}return this},initNthCombo:function(){if(!this.rendered){return}var j=this,f=j.nthCombo,m=f.store,d=j.getStartDate(),o=Ext.Date.getLastDateOfMonth(d).getDate(),i=Ext.Date.format(d,"jS")+" day",e=d.getDate(),a=Math.ceil(e/7),n=Ext.Date.format(d,"D").substring(0,2).toUpperCase(),c=a+Extensible.Number.getOrdinalSuffix(a)+Ext.Date.format(d," l"),k=j.isYearly?" in "+Ext.Date.format(d,"F"):"",g=j.isYearly?"BYMONTH="+Ext.Date.format(d,"n"):"",b=j.isYearly?j.optionDelimiter:"",h=[[i+k,j.isYearly?g:"BYMONTHDAY="+e],[c+k,g+b+"BYDAY="+a+n]],l=m.find("value",f.getValue());if(o-e<7){h.push(["last "+Ext.Date.format(d,"l")+k,g+b+"BYDAY=-1"+n])}if(o===e){h.push(["last day"+k,g+b+"BYMONTHDAY=-1"])}m.removeAll();f.clearValue();m.loadData(h);if(l>h.length-1){l=h.length-1}f.setValue(m.getAt(l>-1?l:0).data.value);return j},getValue:function(){var a=this;if(a.nthCombo){return a.nthCombo.getValue()}return""},setValue:function(c){var e=this;if(!e.preSetValue(c,e.nthCombo)){return e}if(!c){var a=e.nthCombo.store.getAt(0);if(a){e.nthCombo.setValue(a.data.value)}return e}var d=Ext.isArray(c)?c:c.split(e.optionDelimiter),f,b=[];Ext.each(d,function(g){f=g.split("=");if(f[0]==="BYMONTH"){b.unshift(g)}if(f[0]==="BYMONTHDAY"||f[0]==="BYDAY"){b.push(g)}},e);if(b.length){e.nthCombo.setValue(b.join(e.optionDelimiter))}return e}});Ext.define("Extensible.form.recurrence.option.Weekly",{extend:"Extensible.form.recurrence.AbstractOption",alias:"widget.extensible.recurrence-weekly",requires:["Ext.form.field.Checkbox","Ext.form.CheckboxGroup"],dayValueDelimiter:",",cls:"extensible-recur-weekly",getItemConfigs:function(){var a=this.id;return[{xtype:"label",text:"on:"},{xtype:"checkboxgroup",itemId:a+"-days",flex:1,items:[{boxLabel:"Sun",name:"SU",id:a+"-SU"},{boxLabel:"Mon",name:"MO",id:a+"-MO"},{boxLabel:"Tue",name:"TU",id:a+"-TU"},{boxLabel:"Wed",name:"WE",id:a+"-WE"},{boxLabel:"Thu",name:"TH",id:a+"-TH"},{boxLabel:"Fri",name:"FR",id:a+"-FR"},{boxLabel:"Sat",name:"SA",id:a+"-SA"}],listeners:{change:Ext.bind(this.onSelectionChange,this)}}]},initValue:function(){this.callParent(arguments);if(!this.value){this.selectByDate()}},initRefs:function(){this.daysCheckboxGroup=this.down("#"+this.id+"-days")},onSelectionChange:function(c,b,a){this.checkChange();this.updateLabel()},selectByDate:function(b){var a=Ext.Date.format(b||this.getStartDate(),"D").substring(0,2).toUpperCase();this.setValue("BYDAY="+a)},clearValue:function(){this.value=undefined;if(this.daysCheckboxGroup){this.daysCheckboxGroup.setValue({SU:0,MO:0,TU:0,WE:0,TH:0,FR:0,SA:0})}},getValue:function(){var a=this;if(a.daysCheckboxGroup){var c=a.daysCheckboxGroup.getValue(),d=[],b;for(b in c){if(c.hasOwnProperty(b)){d.push(b)}}return d.length>0?"BYDAY="+d.join(a.dayValueDelimiter):""}return""},setValue:function(a){var c=this;if(!c.preSetValue(a,c.daysCheckboxGroup)){return c}if(!a){c.daysCheckboxGroup.setValue(null);return c}var b=Ext.isArray(a)?a:a.split(c.optionDelimiter),e={},d,f;Ext.each(b,function(g){d=g.split("=");if(d[0]==="BYDAY"){f=d[1].split(c.dayValueDelimiter);Ext.each(f,function(h){e[h]=true},c);c.daysCheckboxGroup.setValue(e);return}},c);return c}});Ext.define("Extensible.form.recurrence.option.Yearly",{extend:"Extensible.form.recurrence.option.Monthly",alias:"widget.extensible.recurrence-yearly",cls:"extensible-recur-yearly",nthComboWidth:200,unit:"year"});Ext.define("Extensible.form.recurrence.FrequencyCombo",{extend:"Ext.form.ComboBox",alias:"widget.extensible.recurrence-frequency",requires:["Ext.data.ArrayStore"],fieldLabel:"Repeats",queryMode:"local",triggerAction:"all",forceSelection:true,displayField:"pattern",valueField:"id",cls:"extensible-recur-frequency",frequencyText:{none:"Does not repeat",daily:"Daily",weekdays:"Every weekday (Mon-Fri)",weekly:"Weekly",monthly:"Monthly",yearly:"Yearly"},initComponent:function(){var a=this;a.addEvents("frequencychange");a.frequencyOptions=a.frequencyOptions||[["NONE",a.frequencyText.none],["DAILY",a.frequencyText.daily],["WEEKDAYS",a.frequencyText.weekdays],["WEEKLY",a.frequencyText.weekly],["MONTHLY",a.frequencyText.monthly],["YEARLY",a.frequencyText.yearly]];a.store=a.store||Ext.create("Ext.data.ArrayStore",{fields:["id","pattern"],idIndex:0,data:a.frequencyOptions});a.on("select",a.onSelect,a);a.callParent(arguments)},onSelect:function(b,a){this.fireEvent("frequencychange",a[0].data.id)}});Ext.define("Extensible.form.recurrence.Fieldset",{extend:"Ext.form.FieldContainer",alias:"widget.extensible.recurrencefield",mixins:{field:"Ext.form.field.Field"},requires:["Ext.form.Label","Extensible.form.recurrence.FrequencyCombo","Extensible.form.recurrence.option.Interval","Extensible.form.recurrence.option.Weekly","Extensible.form.recurrence.option.Monthly","Extensible.form.recurrence.option.Yearly","Extensible.form.recurrence.option.Duration"],options:["daily","weekly","weekdays","monthly","yearly"],displayStyle:"field",fieldLabel:"Repeats",fieldContainerWidth:400,startDate:Ext.Date.clearTime(new Date()),monitorChanges:true,cls:"extensible-recur-field",frequencyWidth:null,layout:"anchor",defaults:{anchor:"100%"},initComponent:function(){var a=this;if(!a.height||a.displayStyle==="field"){delete a.height;a.autoHeight=true}a.items=[{xtype:"extensible.recurrence-frequency",hideLabel:true,width:this.frequencyWidth,itemId:this.id+"-frequency",listeners:{frequencychange:{fn:this.onFrequencyChange,scope:this}}},{xtype:"container",itemId:this.id+"-inner-ct",cls:"extensible-recur-inner-ct",autoHeight:true,layout:"anchor",hideMode:"offsets",hidden:true,width:this.fieldContainerWidth,defaults:{hidden:true},items:[{xtype:"extensible.recurrence-interval",itemId:this.id+"-interval"},{xtype:"extensible.recurrence-weekly",itemId:this.id+"-weekly"},{xtype:"extensible.recurrence-monthly",itemId:this.id+"-monthly"},{xtype:"extensible.recurrence-yearly",itemId:this.id+"-yearly"},{xtype:"extensible.recurrence-duration",itemId:this.id+"-duration"}]}];a.callParent(arguments);a.initField()},afterRender:function(){this.callParent(arguments);this.initRefs()},initRefs:function(){var a=this,b=a.id;a.innerContainer=a.down("#"+b+"-inner-ct");a.frequencyCombo=a.down("#"+b+"-frequency");a.intervalField=a.down("#"+b+"-interval");a.weeklyField=a.down("#"+b+"-weekly");a.monthlyField=a.down("#"+b+"-monthly");a.yearlyField=a.down("#"+b+"-yearly");a.durationField=a.down("#"+b+"-duration");a.initChangeEvents()},initChangeEvents:function(){var a=this;a.intervalField.on("change",a.onChange,a);a.weeklyField.on("change",a.onChange,a);a.monthlyField.on("change",a.onChange,a);a.yearlyField.on("change",a.onChange,a);a.durationField.on("change",a.onChange,a)},onChange:function(){this.fireEvent("change",this,this.getValue())},onFrequencyChange:function(a){this.setFrequency(a);this.onChange()},initValue:function(){var a=this;a.originalValue=a.lastValue=a.value;a.suspendCheckChange++;a.setStartDate(a.startDate);if(a.value!==undefined){a.setValue(a.value)}else{if(a.frequency!==undefined){a.setValue("FREQ="+a.frequency)}else{a.setValue("")}}a.suspendCheckChange--;Ext.defer(a.doLayout,1,a);a.onChange()},setStartDate:function(b){var a=this;a.startDate=b;if(a.innerContainer){a.innerContainer.items.each(function(c){if(c.setStartDate){c.setStartDate(b)}})}else{a.on("afterrender",function(){a.setStartDate(b)},a,{single:true})}return a},getStartDate:function(){return this.startDate},isRecurring:function(){return this.getValue()!==""},getValue:function(){if(!this.innerContainer){return this.value}if(this.frequency==="NONE"){return""}var a,b;if(this.frequency==="WEEKDAYS"){a=["FREQ=WEEKLY","BYDAY=MO,TU,WE,TH,FR"]}else{a=["FREQ="+this.frequency]}this.innerContainer.items.each(function(c){if(c.isVisible()&&c.getValue){b=c.getValue();if(this.includeItemValue(b)){a.push(b)}}},this);return a.length>1?a.join(";"):a[0]},includeItemValue:function(b){if(b){if(b==="INTERVAL=1"){return false}var a=Ext.Date.format(this.startDate,"D").substring(0,2).toUpperCase();if(b===("BYDAY="+a)){return false}return true}return false},getDescription:function(){var a=this.getValue(),b="";return"Friendly text : "+b},setValue:function(b){var a=this;a.value=(!b||b==="NONE"?"":b);if(!a.frequencyCombo||!a.innerContainer){a.on("afterrender",function(){a.setValue(b)},a,{single:true});return}var c=a.value.split(";");if(a.value===""){a.setFrequency("NONE")}else{Ext.each(c,function(d){if(d.indexOf("FREQ")>-1){var e=d.split("=")[1];a.setFrequency(e);a.checkChange();return}},a)}a.innerContainer.items.each(function(d){if(d.setValue){d.setValue(a.value)}});a.checkChange();return a},setFrequency:function(b){var a=this;a.frequency=b;if(a.frequencyCombo){a.frequencyCombo.setValue(b);a.showOptions(b)}else{a.on("afterrender",function(){a.frequencyCombo.setValue(b);a.showOptions(b)},a,{single:true})}return a},showOptions:function(c){var b=this,a="day";if(c==="NONE"){b.innerContainer.hide()}else{b.intervalField.show();b.durationField.show();b.innerContainer.show()}switch(c){case"DAILY":case"WEEKDAYS":b.weeklyField.hide();b.monthlyField.hide();b.yearlyField.hide();if(c==="WEEKDAYS"){a="week"}break;case"WEEKLY":b.weeklyField.show();b.monthlyField.hide();b.yearlyField.hide();a="week";break;case"MONTHLY":b.monthlyField.show();b.weeklyField.hide();b.yearlyField.hide();a="month";break;case"YEARLY":b.yearlyField.show();b.weeklyField.hide();b.monthlyField.hide();a="year";break}b.intervalField.updateLabel(a)}});Ext.define("Extensible.form.recurrence.RangeEditPanel",{extend:"Ext.form.Panel",alias:"widget.extensible.recurrence-rangeeditpanel",cls:"extensible-recur-edit-options",headerText:"There are multiple events in this series. How would you like your changes applied?",optionSingleButtonText:"Single",optionSingleDescription:"Apply to this event only. No other events in the series will be affected.",optionFutureButtonText:"Future",optionFutureDescription:"Apply to this and all following events only. Past events will be unaffected.",optionAllButtonText:"All Events",optionAllDescription:"Apply to every event in this series.",editModes:{single:"single",future:"future",all:"all"},border:false,layout:{type:"vbox",align:"stretch"},initComponent:function(){var a=this;a.editMode=a.editModes.single;a.items=[a.getHeaderConfig(),a.getOptionPanelConfig(),a.getSummaryConfig()];a.callParent(arguments)},getHeaderConfig:function(){return{xtype:"component",html:this.headerText,height:55,padding:15}},getSummaryConfig:function(){return{xtype:"component",itemId:this.id+"-summary",html:this.optionSingleDescription,flex:1,padding:15}},getOptionPanelConfig:function(){return{xtype:"panel",border:false,layout:{type:"hbox",pack:"center"},items:this.getOptionButtonConfigs()}},getOptionButtonConfigs:function(){var c=this,a={xtype:"button",iconAlign:"top",enableToggle:true,scale:"large",width:80,toggleGroup:"recur-toggle",toggleHandler:c.onToggle,scope:c},b=[Ext.apply({itemId:c.id+"-single",text:c.optionSingleButtonText,iconCls:"recur-edit-single",pressed:c.editMode===c.editModes.single},a),Ext.apply({itemId:c.id+"-future",text:c.optionFutureButtonText,iconCls:"recur-edit-future",pressed:c.editMode===c.editModes.future},a),Ext.apply({itemId:c.id+"-all",text:c.optionAllButtonText,iconCls:"recur-edit-all",pressed:c.editMode===c.editModes.all},a)];return b},getEditMode:function(){return this.editMode},showEditModes:function(e){e=e||[];var d=this,c=0,b,a=e.length;d.down("#"+d.id+"-single")[a?"hide":"show"]();d.down("#"+d.id+"-future")[a?"hide":"show"]();d.down("#"+d.id+"-all")[a?"hide":"show"]();for(;c<a;c++){b=d.down("#"+d.id+"-"+e[c]);if(b){b.show()}}},onToggle:function(a){var c=this,b=c.getComponent(c.id+"-summary").getEl();if(a.itemId===c.id+"-single"){b.update(c.optionSingleDescription);c.editMode=c.editModes.single}else{if(a.itemId===c.id+"-future"){b.update(c.optionFutureDescription);c.editMode=c.editModes.future}else{b.update(c.optionAllDescription);c.editMode=c.editModes.all}}}});Ext.define("Extensible.form.recurrence.RangeEditWindow",{extend:"Ext.window.Window",alias:"widget.extensible.recurrence-rangeeditwindow",singleton:true,requires:["Extensible.form.recurrence.RangeEditPanel"],title:"Recurring Event Options",width:350,height:240,saveButtonText:"Save",cancelButtonText:"Cancel",closeAction:"hide",modal:true,resizable:false,constrain:true,buttonAlign:"right",layout:"fit",formPanelConfig:{border:false},initComponent:function(){this.items=[{xtype:"extensible.recurrence-rangeeditpanel",itemId:this.id+"-recur-panel"}];this.fbar=this.getFooterBarConfig();this.callParent(arguments)},getRangeEditPanel:function(){return this.down("#"+this.id+"-recur-panel")},prompt:function(a){this.callbackFunction=Ext.bind(a.callback,a.scope||this);this.getRangeEditPanel().showEditModes(a.editModes);this.show()},getFooterBarConfig:function(){var a=["->",{text:this.saveButtonText,itemId:this.id+"-save-btn",disabled:false,handler:this.onSaveAction,scope:this},{text:this.cancelButtonText,itemId:this.id+"-cancel-btn",disabled:false,handler:this.onCancelAction,scope:this}];return a},onSaveAction:function(){var a=this.getComponent(this.id+"-recur-panel").getEditMode();this.callbackFunction(a);this.close()},onCancelAction:function(){this.callbackFunction(false);this.close()}});