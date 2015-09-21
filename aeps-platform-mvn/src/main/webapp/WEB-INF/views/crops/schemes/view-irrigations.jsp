<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<div id="divListIrr">
    <%@ include file="../irrigations/info-irrigations.jsp" %>            
</div>
 <div class="row">
                        <div class="span5">
                            <div class="control-group">
                                <label for="formCropDes_desPro_obsDesPro" class="control">
                                   Observaciones en riegos
                                </label>
                                <div class="controls">
                                    <s:textarea rows="5" name="desPro.obsDesPro"></s:textarea>
                                </div> 
                            </div>                          
                        </div>     
   </div>
