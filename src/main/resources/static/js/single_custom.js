/* JS Document */

/******************************

[Table of Contents]

1. Vars and Inits
2. Set Header
3. Init Menu
4. Init Thumbnail
5. Init Quantity
6. Init Star Rating
7. Init Favorite
8. Init Tabs



******************************/

jQuery(document).ready(function($)
{
	"use strict";
	const stockNum = document.querySelector('.stockNum');
	/* 

	1. Vars and Inits

	*/

	var header = $('.header');
	var topNav = $('.top_nav')
	var hamburger = $('.hamburger_container');
	var menu = $('.hamburger_menu');
	var menuActive = false;
	var hamburgerClose = $('.hamburger_close');
	var fsOverlay = $('.fs_menu_overlay');

	setHeader();

	$(window).on('resize', function()
	{
		setHeader();
	});

	$(document).on('scroll', function()
	{
		setHeader();
	});

	initMenu();
	// initThumbnail();
	// const quantitySelector = document.getElementById('quantity_selector');
	// if(quantitySelector){
		initQuantity();
	// }
	// initStarRating();
	initFavorite();
	initTabs();

	/* 

	2. Set Header

	*/

	function setHeader()
	{
		if(window.innerWidth < 992)
		{
			if($(window).scrollTop() > 100)
			{
				header.css({'top':"0"});
			}
			else
			{
				header.css({'top':"0"});
			}
		}
		else
		{
			if($(window).scrollTop() > 100)
			{
				header.css({'top':"-50px"});
			}
			else
			{
				header.css({'top':"0"});
			}
		}
		if(window.innerWidth > 991 && menuActive)
		{
			closeMenu();
		}
	}

	/* 

	3. Init Menu

	*/

	function initMenu()
	{
		if(hamburger.length)
		{
			hamburger.on('click', function()
			{
				if(!menuActive)
				{
					openMenu();
				}
			});
		}

		if(fsOverlay.length)
		{
			fsOverlay.on('click', function()
			{
				if(menuActive)
				{
					closeMenu();
				}
			});
		}

		if(hamburgerClose.length)
		{
			hamburgerClose.on('click', function()
			{
				if(menuActive)
				{
					closeMenu();
				}
			});
		}

		if($('.menu_item').length)
		{
			var items = document.getElementsByClassName('menu_item');
			var i;

			for(i = 0; i < items.length; i++)
			{
				if(items[i].classList.contains("has-children"))
				{
					items[i].onclick = function()
					{
						this.classList.toggle("active");
						var panel = this.children[1];
					    if(panel.style.maxHeight)
					    {
					    	panel.style.maxHeight = null;
					    }
					    else
					    {
					    	panel.style.maxHeight = panel.scrollHeight + "px";
					    }
					}
				}	
			}
		}
	}

	function openMenu()
	{
		menu.addClass('active');
		// menu.css('right', "0");
		fsOverlay.css('pointer-events', "auto");
		menuActive = true;
	}

	function closeMenu()
	{
		menu.removeClass('active');
		fsOverlay.css('pointer-events', "none");
		menuActive = false;
	}
	/* 

	5. Init Quantity

	*/

	function initQuantity() {
		const totalCount = document.getElementById('totalCount');
		const totalPrice = document.getElementById('totalPrice');
		const originalPrice = document.getElementById('originalPrice');
		const plusButton = document.querySelector('.plus');
		const minusButton = document.querySelector('.minus');
		const valueElement = document.querySelector('.quantity_value')

		let quantity = parseInt(valueElement.textContent); // 현재 수량 가져오기
		let stockNumber = parseInt(stockNum.value); // 상품 재고 수량
		let resultPrice;
		plusButton.addEventListener('click', function () {
			if (valueElement.textContent < stockNumber) {
				quantity += 1;
				valueElement.textContent = quantity;
				totalCount.textContent = quantity;
				resultPrice = parseInt(originalPrice.value)*quantity;
				totalPrice.textContent = resultPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')+"원";
			}
		});
		minusButton.addEventListener('click', function () {
			if (quantity > 1) {
				quantity -= 1;
				valueElement.textContent = quantity;
				totalCount.textContent = quantity;
				resultPrice = parseInt(originalPrice.value)*quantity;
				totalPrice.textContent = resultPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')+"원";
			}
		});

	}

	/* 

	7. Init Favorite

	*/

	function initFavorite() {
		if ($('.product_favorite').length) {
			var fav = $('.product_favorite');

			fav.on('click', function () {
				fav.toggleClass('active');
			});
		}
	}

	/* 

	8. Init Tabs

	*/

	function initTabs()
	{
		if($('.tabs').length)
		{
			var tabs = $('.tabs li');
			var tabContainers = $('.tab_container');

			tabs.each(function()
			{
				var tab = $(this);
				var tab_id = tab.data('active-tab');

				tab.on('click', function()
				{
					if(!tab.hasClass('active'))
					{
						tabs.removeClass('active');
						tabContainers.removeClass('active');
						tab.addClass('active');
						$('#' + tab_id).addClass('active');
					}
				});
			});
		}
	}
});

function selectImg(index){
	const itemSelectImage = document.getElementById('itemSelectImage'+index);
	const itemMainImage = document.getElementById('itemMainImage');
	itemMainImage.src=itemSelectImage.src;
}