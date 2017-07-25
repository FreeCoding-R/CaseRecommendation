// Page ready
$(document).ready(function()
{
	$('body').append('<div id="page-loading-blocs-notifaction"></div>'); // Add page loading UI
	$('.bloc-fill-screen').css('height', $(window).height()+'px'); // Set initial hero height
	$('#scroll-hero').click(function(event)
	{
		event.preventDefault();
		$('html,body').animate({scrollTop: $('#scroll-hero').closest('.bloc').height()}, 'slow');
	});
    $('.scroll-hero').click(function(event)
    {
        event.preventDefault();
        let height=0;
        for(let i=0;i<$('.bloc').length;i++){
            height+=$('.bloc:eq('+i+')').height();
            if($(this).closest('.bloc').is($('.bloc:eq('+i+')'))){
                break;
            }
        }
        $('html,body').animate({scrollTop: height}, 'slow');
    });
	
	hideNavOnItemClick(); // Hide Menu On Item Click
	setUpSpecialNavs(); // Set Up Special NavBars 
	setUpDropdownSubs(); // Set Up Dropdown Menu Support
	setUpLightBox(); // Add lightbox Support
	setUpVisibilityToggle(); // Add visibility Toggle Support
});

// Loading page complete
$(window).load(function()
{
	setFillScreenBlocHeight();
	animateWhenVisible();  // Activate animation when visible	
	$('#page-loading-blocs-notifaction').remove(); // Remove page loading UI
}
).resize(function() // Window resize 
{		
	setFillScreenBlocHeight();	
}); 

// Set Up Special NavBars 
function setUpSpecialNavs()
{
	$('.navbar-toggle').click(function(e)
	{ 
		var targetNav = $(this).closest('nav');
		var targetMenu = targetNav.find('ul.site-navigation');
		var newMenu = targetMenu.clone();
		
		if(targetMenu.parent().hasClass('nav-special')) // Nav is Special
		{
			e.stopPropagation(); // Dont do this is normal menu in use
			if(!$(this).hasClass('selected-nav')) // Open Menu
			{
				$(this).addClass('selected-nav');
				var navClasses = targetNav.attr('class').replace('navbar','').replace('row','');
				var menuClasses = targetMenu.parent().attr('class').replace('navbar-collapse','').replace('collapse','');
				
				if($('.content-tint').length =-1)
				{
					$('body').append('<div class="content-tint"></div>');
				}
				
				
				newMenu.insertBefore('.page-container').wrap('<div class="blocsapp-special-menu '+navClasses+'"><blocsnav class="'+menuClasses+'">');
				$('blocsnav').prepend('<a class="close-special-menu animated fadeIn" style="animation-delay:0.5s;"><div class="close-icon"></div></a>');
				
				animateNavItems(); // Animate Nav Items

				setTimeout(function(){
					$('.blocsapp-special-menu blocsnav').addClass('open');
					$('.content-tint').addClass('on');
					$('body').addClass('lock-scroll');
				}, 10);
			}
			else // Close menu
			{
				$('.blocsapp-special-menu blocsnav').removeClass('open');
				$('.selected-nav').removeClass('selected-nav');
				setTimeout(function(){
					$('.blocsapp-special-menu').remove();
					$('body').removeClass('lock-scroll');	
					$('.selected-nav').removeClass('selected-nav');
				}, 300);
			}
		}
	});
	
	// Close Special Menu with Tint Click	
	$('body').on("mousedown touchstart",".content-tint, .close-special-menu",function(e)
	{	
		$('.content-tint').removeClass('on');
		$('.selected-nav').click();
	
		setTimeout(function(){
			$('.content-tint').remove();
		}, 10);
	}
	).on("click",".blocsapp-special-menu a",function(e) // Close Menu On Link Click
	{	
		if(!$(e.target).closest('.dropdown-toggle').length)
		{
			$('.close-special-menu').mousedown();
		} 
	});
	
	// Animate Nav Items
	function animateNavItems()
	{
		var animationStyle = 'fadeInRight';
		var delay = 0;
		var increaseVal = 60;
	
		if($('.blocsapp-special-menu blocsnav').hasClass('fullscreen-nav'))
		{
			animationStyle = 'fadeIn';
			increaseVal = 100;
		}
		else if($('.blocsapp-special-menu').hasClass('nav-invert')) // Inverted Nav
		{
			animationStyle = 'fadeInLeft';
		}
	
		$('.blocsapp-special-menu blocsnav li').each(function()
		{
			if($(this).parent().hasClass('dropdown-menu')) // Not A drop down
			{
				$(this).addClass('animated fadeIn');	
			}
			else
			{
				delay += increaseVal; 
				$(this).attr('style','animation-delay:'+delay+'ms').addClass('animated '+animationStyle);	
			}
		});
	}
}


// Hide Menu On Item Click
function hideNavOnItemClick()
{
	$(".site-navigation a").click(function(e)
	{
		if(!$(e.target).closest('.dropdown-toggle').length) // Prevent Dropdowns Closing on click
		{
			$(".navbar-collapse").collapse('hide');
		}
	});
}


// Set Fill Screen Bloc heights
function setFillScreenBlocHeight()
{
	$('.bloc-fill-screen').each(function(i) // Loop all fill Screens
	{
		var parentFillDiv = $(this);
		window.fillBodyHeight = 0;
		$(this).find('.container').each(function(i) // Loop all fill Screens
		{
			fillPadding = parseInt($(this).css('padding-top'))*2
			
			if(parentFillDiv.hasClass('bloc-group')) // Bloc Groups
			{
				fillBodyHeight = fillPadding + $(this).outerHeight()+50; // Set hero body height
			}
			else
			{
				fillBodyHeight = fillBodyHeight + fillPadding + $(this).outerHeight()+50; // Set hero body height
			}
		});
		$(this).css('height', (getFillHeight()) + 'px'); // Set Fill height
	});
}

// Get Fill Height
function getFillHeight()
{
	var H = $(window).height(); // Window height
	
	if(H < fillBodyHeight) // If window height is less than content height
	{
		H = fillBodyHeight+100;
	}
	return H
}

// Scroll to target
function scrollToTarget(D)
{
	if(D == 1) // Top of page
	{
		D = 0;
	}
	else if(D == 2) // Bottom of page
	{
		D = $(document).height();
	}
	else // Specific Bloc
	{
		D = $(D).offset().top;
		if($('.sticky-nav').length) // Sticky Nav in use
		{
			D = D-$('.sticky-nav').height();
		}
	}

	$('html,body').animate({scrollTop:D}, 'slow');
	$(".navbar-collapse").collapse('hide');	
}

// Initial tooltips
$(function()
{
  $('[pythondata-toggle="tooltip"]').tooltip()
})


// Animate when visible
function animateWhenVisible()
{
	hideAll(); // Hide all animation elements
	inViewCheck(); // Initail check on page load
	
	$(window).scroll(function()
	{		
		inViewCheck(); // Check object visability on page scroll
		scrollToTopView(); // ScrollToTop button visability toggle
		stickyNavToggle(); // Sticky nav toggle
	});		
};

// Set Up Dropdown Menu Support
function setUpDropdownSubs()
{
	$('ul.dropdown-menu [pythondata-toggle=dropdown]').on('click', function(event)
	{
		event.preventDefault(); 
		event.stopPropagation(); 
		$(this).parent().siblings().removeClass('open');
		$(this).parent().toggleClass('open');
		
		var targetMenu = $(this).parent().children('.dropdown-menu');
		var leftVal = targetMenu.offset().left+targetMenu.width();
		if(leftVal > $(window).width())
		{
			targetMenu.addClass('dropmenu-flow-right');
		}
	});
}

// Hide all animation elements
function stickyNavToggle()
{
	var V = 0; // offset Value
	var C = "sticky"; // Classes
	
	if($('.sticky-nav').hasClass('fill-bloc-top-edge')) // If nav is in hero animate in
	{
		var heroBackgroundColor = $('.fill-bloc-top-edge.sticky-nav').parent().css('background-color');

		if(heroBackgroundColor == "rgba(0, 0, 0, 0)")
		{
			heroBackgroundColor = '#FFFFFF'
		}
		
		$('.sticky-nav').css('background', heroBackgroundColor); 
		
		V = $('.sticky-nav').height();
		C = "sticky animated fadeInDown";
	}
	
	if($(window).scrollTop() > V)
	{  
		$('.sticky-nav').addClass(C);
		
		if(C == "sticky")
		{
			$('.page-container').css('padding-top',$('.sticky-nav').height());
		}
	}
	else
	{
		$('.sticky-nav').removeClass(C).removeAttr('style');
		$('.page-container').removeAttr('style');
	}	
}

// Hide all animation elements
function hideAll()
{
	$('.animated').each(function(i)
	{	
		if(!$(this).closest('.hero').length) // Dont hide hero object
		{
			$(this).removeClass('animated').addClass('hideMe');
		}
	});
}

// Check if object is inView
function inViewCheck()
{	
	$($(".hideMe").get().reverse()).each(function(i)
	{	
		var target = jQuery(this);
		var a = target.offset().top + target.height();
		var b = $(window).scrollTop() + $(window).height();
		
		if(target.height() > $(window).height()) // If object height is greater than window height
		{
			a = target.offset().top;
		}
		
		if (a < b) 
		{	
			var objectClass = target.attr('class').replace('hideMe' , 'animated');
			target.css('visibility','hidden').removeAttr('class');
			setTimeout(function(){target.attr('class',objectClass).css('visibility','visible');},0.01);				
		}
	});
};

// ScrollToTop button toggle
function scrollToTopView()
{
	if($(window).scrollTop() > $(window).height()/3)
	{	
		if(!$('.scrollToTop').hasClass('showScrollTop'))
		{
			$('.scrollToTop').addClass('showScrollTop');
		}	
	}
	else
	{
		$('.scrollToTop').removeClass('showScrollTop');
	}
};


// Toggle Visibility
function setUpVisibilityToggle()
{
	$(document).on('click', '[pythondata-toggle-visibility]', function(e)
	{
		e.preventDefault();
		var targetID = $(this).attr('pythondata-toggle-visibility');
		if(targetID.indexOf(',')!=-1) // Is Array
		{
			var targeArray = targetID.split(',');
			
			$.each(targeArray, function(i) // Loop Array
			{
				toggleVisibility($('#'+targeArray[i]));
			});
		}
		else // Single
		{
			toggleVisibility($('#'+targetID));
		}
		
		function toggleVisibility(T)
		{
			if(T.is('img')) // Image
			{
				T.toggle();
			}
			else // Other
			{
				T.slideToggle();
			}
		}
	});
}

// Light box support
function setUpLightBox()
{
	window.targetLightbox;
	
	$(document).on('click', '[pythondata-lightbox]', function(e) // Create Lightbox Modal
	{
		e.preventDefault();
		targetLightbox = $(this);
		var lightBoxPath = targetLightbox.attr('pythondata-lightbox');
		var lightBoxAutoPlay = targetLightbox.attr('pythondata-autoplay');
		var captionData ='<p class="lightbox-caption">'+targetLightbox.attr('pythondata-caption')+'</p>';
		var galleryID = 'no-gallery-set';
		
		if(targetLightbox.attr('pythondata-gallery-id')) // Has a gallery ID so use it
		{
			galleryID = targetLightbox.attr('pythondata-gallery-id');
		}
		
		if(!targetLightbox.attr('pythondata-caption')) // No caption caption pythondata
		{
			captionData = '';
		}
		
		var autoplay = ""; // No Auto Play default

		if(lightBoxAutoPlay == 1) // Add Auto Play
		{
			autoplay = "autoplay";
		}
		
		var customModal = $('<div id="lightbox-modal" class="modal fade"><div class="modal-dialog"><div class="modal-content '+targetLightbox.attr('pythondata-frame')+' blocs-lb-container"><button type="button" class="close close-lightbox" pythondata-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button><div class="modal-body"><a href="#" class="prev-lightbox" aria-label="prev"><span class="fa fa-chevron-left"></span></a><a href="#" class="next-lightbox" aria-label="next"><span class="fa fa-chevron-right"></span></a><img id="lightbox-image" class="img-responsive" src="'+lightBoxPath+'"><div id="lightbox-video-container" class="embed-responsive embed-responsive-16by9"><video controls '+autoplay+' class="embed-responsive-item"><source id="lightbox-video" src="'+lightBoxPath+'" type="video/mp4"></video></div>'+captionData+'</div></div></div></div>');
		$('body').append(customModal);
		
		if(lightBoxPath.substring(lightBoxPath.length-4) == ".mp4") // Video Object
		{
			$('#lightbox-image, .lightbox-caption').hide();
			$('#lightbox-video-container').show();
		}
		else // Image Object
		{
			$('#lightbox-image,.lightbox-caption').show();
			$('#lightbox-video-container').hide();
		}
		
		$('#lightbox-modal').modal('show');
		
		if(galleryID == 'no-gallery-set') // No Gallery ID
		{
			// Handle navigation buttons (next - prev)
			if($('a[pythondata-lightbox]').index(targetLightbox) == 0)
			{
				$('.prev-lightbox').hide();
			}
			if($('a[pythondata-lightbox]').index(targetLightbox) == $('a[pythondata-lightbox]').length-1)
			{
				$('.next-lightbox').hide();
			}
		}
		else // Has Gallery ID
		{
			// Handle navigation buttons (next - prev)
			if($('a[pythondata-gallery-id="'+galleryID+'"]').index(targetLightbox) == 0)
			{
				$('.prev-lightbox').hide();
			}
			if($('a[pythondata-gallery-id="'+galleryID+'"]').index(targetLightbox) == $('a[pythondata-gallery-id="'+galleryID+'"]').length-1)
			{
				$('.next-lightbox').hide();
			}
		}
	}
	).on('hidden.bs.modal', '#lightbox-modal', function () // Handle destroy modal 
	{
		$('#lightbox-modal').remove();
	})
	
	$(document).on('click', '.next-lightbox, .prev-lightbox', function(e) 
	{
		e.preventDefault();
		var galleryID = 'no-gallery-set';
		var idx = $('a[pythondata-lightbox]').index(targetLightbox);
		var next = $('a[pythondata-lightbox]').eq(idx+1) // Next
		
		if(targetLightbox.attr('pythondata-gallery-id')) // Has Gallery ID so Use
		{
			galleryID = targetLightbox.attr('pythondata-gallery-id'); // ID
			idx = $('a[pythondata-gallery-id="'+galleryID+'"]').index(targetLightbox); // Index
			next = $('a[pythondata-gallery-id="'+galleryID+'"]').eq(idx+1) // Next
		}
		
		if($(this).hasClass('prev-lightbox'))
		{
			next = $('a[pythondata-gallery-id="'+galleryID+'"]').eq(idx-1) // Prev
			
			if(galleryID == 'no-gallery-set') // No Gallery ID
			{
				next = $('a[pythondata-lightbox]').eq(idx-1) // Prev
			}
		}
		
		var nextContentPath = next.attr('pythondata-lightbox');
		
		if(nextContentPath.substring(nextContentPath.length-4) == ".mp4") // Video Object
		{
			var lightBoxAutoPlay = next.attr('pythondata-autoplay');
			var autoplay = ""; // No Auto Play default

			if(lightBoxAutoPlay == 1) // Add Auto Play
			{
				autoplay = "autoplay";
			}
			
			$('#lightbox-image, .lightbox-caption').hide();
			$('#lightbox-video-container').show().html('<video controls '+autoplay+' class="embed-responsive-item"><source id="lightbox-video" src="'+nextContentPath+'" type="video/mp4"></video>');	
		}
		else // Image Object
		{
			$('#lightbox-image').attr('src',nextContentPath).show();
			$('.lightbox-caption').html(next.attr('pythondata-caption')).show();
			$('#lightbox-video-container').hide();
		}
		
		targetLightbox = next;	
		
		// Handle navigation buttons (next - prev)
		$('.next-lightbox, .prev-lightbox').hide();	
		
		if(galleryID == 'no-gallery-set') // No Gallery ID
		{
			if($('a[pythondata-lightbox]').index(next) != $('a[pythondata-lightbox]').length-1)
			{
				$('.next-lightbox').show();
			}
			if($('a[pythondata-lightbox]').index(next) > 0)
			{
				$('.prev-lightbox').show();
			}
		}
		else // Has Gallery ID
		{
			if($('a[pythondata-gallery-id="'+galleryID+'"]').index(next) != $('a[pythondata-gallery-id="'+galleryID+'"]').length-1)
			{
				$('.next-lightbox').show();
			}
			if($('a[pythondata-gallery-id="'+galleryID+'"]').index(next) > 0)
			{
				$('.prev-lightbox').show();
			}
		}
	});
}