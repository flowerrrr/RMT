; initialize datetime after introducing new field.
update event set datetime = addtime(date, time);