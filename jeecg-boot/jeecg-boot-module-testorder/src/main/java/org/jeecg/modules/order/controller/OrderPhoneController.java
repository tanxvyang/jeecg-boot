package org.jeecg.modules.order.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.order.entity.OrderPhone;
import org.jeecg.modules.order.service.IOrderPhoneService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 订单表手机应用
 * @Author: jeecg-boot
 * @Date:   2020-12-03
 * @Version: V1.0
 */
@Api(tags="订单表手机应用")
@RestController
@RequestMapping("/order/orderPhone")
@Slf4j
public class OrderPhoneController extends JeecgController<OrderPhone, IOrderPhoneService> {
	@Autowired
	private IOrderPhoneService orderPhoneService;
	
	/**
	 * 分页列表查询
	 *
	 * @param orderPhone
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "订单表手机应用-分页列表查询")
	@ApiOperation(value="订单表手机应用-分页列表查询", notes="订单表手机应用-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OrderPhone orderPhone,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OrderPhone> queryWrapper = QueryGenerator.initQueryWrapper(orderPhone, req.getParameterMap());
		Page<OrderPhone> page = new Page<OrderPhone>(pageNo, pageSize);
		IPage<OrderPhone> pageList = orderPhoneService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param orderPhone
	 * @return
	 */
	@AutoLog(value = "订单表手机应用-添加")
	@ApiOperation(value="订单表手机应用-添加", notes="订单表手机应用-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OrderPhone orderPhone) {
		orderPhoneService.save(orderPhone);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param orderPhone
	 * @return
	 */
	@AutoLog(value = "订单表手机应用-编辑")
	@ApiOperation(value="订单表手机应用-编辑", notes="订单表手机应用-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OrderPhone orderPhone) {
		orderPhoneService.updateById(orderPhone);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单表手机应用-通过id删除")
	@ApiOperation(value="订单表手机应用-通过id删除", notes="订单表手机应用-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		orderPhoneService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单表手机应用-批量删除")
	@ApiOperation(value="订单表手机应用-批量删除", notes="订单表手机应用-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.orderPhoneService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单表手机应用-通过id查询")
	@ApiOperation(value="订单表手机应用-通过id查询", notes="订单表手机应用-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		OrderPhone orderPhone = orderPhoneService.getById(id);
		if(orderPhone==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(orderPhone);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param orderPhone
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OrderPhone orderPhone) {
        return super.exportXls(request, orderPhone, OrderPhone.class, "订单表手机应用");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OrderPhone.class);
    }

}
