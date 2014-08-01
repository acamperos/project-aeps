<s:date name="dateFer" format="dd/MM/yyyy" var="dateTransformRowFer"/>
<td><s:property value="%{infoFert.get('ferTyp')}" /></td>
<td><s:property value="%{infoFert.get('amountUsed')}" /></td>
<s:set name="useFerTyp" value="%{infoFert.get('idFerTyp')}"/>
<s:if test="%{#useFerTyp!=null}">
    <td><s:property value="%{infoFert.get('nameFerTyp')}" /></td>
</s:if> 
<s:elseif test="%{#useFerTyp==null}">
    <td><s:property value="%{infoFert.get('otherProduct')}" /></td>
</s:elseif>               