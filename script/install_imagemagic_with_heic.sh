# inspired by https://gist.github.com/rjnienaber/af47fccb8410926ba7ea35f96c3b87fd

# remove bundled ImageMagick
sudo apt remove imagemagick -y

# install base dependencies
sudo apt-get install -y \
  build-essential \
  git \
  libde265-dev \
  libdjvulibre-dev \
  libfftw3-dev \
  libghc-bzlib-dev \
  libgoogle-perftools-dev \
  libgraphviz-dev \
  libgs-dev \
  libheif-dev \
  libjbig-dev \
  libjemalloc-dev \
  libjpeg-dev \
  liblcms2-dev \
  liblqr-1-0-dev \
  liblzma-dev \
  libopenexr-dev \
  libopenjp2-7-dev \
  libpango1.0-dev \
  libraqm-dev \
  libraw-dev \
  librsvg2-dev \
  libtiff-dev \
  libwebp-dev \
  libwmf-dev \
  libxml2-dev \
  libzip-dev \
  libzstd-dev

# installing
git clone --depth 1 --branch 7.1.0-54 https://github.com/ImageMagick/ImageMagick.git
cd ImageMagick

# options omitted from the configure
#	--with-autotrace=yes \ # requires autotrace from the github, looks abandoned
#	--with-dps=yes \  # ?
#	--with-flif=yes \ # ?
#	--with-fpx=yes \  # ?
#	--with-jxl=yes \  # requires libjxl from the github
#	--with-ltdl=no \  # ?

./configure \
  --with-bzlib=yes \
  --with-djvu=yes \
  --with-dps=yes \
  --with-fftw=yes \
  --with-flif=yes \
  --with-fontconfig=yes \
  --with-fpx=yes \
  --with-freetype=yes \
  --with-gslib=yes \
  --with-gvc=yes \
  --with-heic=yes \
  --with-jbig=yes \
  --with-jemalloc=yes \
  --with-jpeg=yes \
  --with-jxl=yes \
  --with-lcms=yes \
  --with-lqr=yes \
  --with-lzma=yes \
  --with-magick-plus-plus=yes \
  --with-openexr=yes \
  --with-openjp2=yes \
  --with-pango=yes \
  --with-perl=yes \
  --with-png=yes \
  --with-raqm=yes \
  --with-raw=yes \
  --with-rsvg=yes \
  --with-tcmalloc=yes \
  --with-tiff=yes \
  --with-webp=yes \
  --with-wmf=yes \
  --with-x=yes \
  --with-xml=yes \
  --with-zip=yes \
  --with-zlib=yes \
  --with-zstd=yes \
  --with-gcc-arch=native

# ==============================================================================
# ImageMagick 7.1.0-54 is configured as follows. Please verify that this
# configuration matches your expectations.
# 
# Host system type: x86_64-pc-linux-gnu
# Build system type: x86_64-pc-linux-gnu
# 
#                   Option                        Value
# ------------------------------------------------------------------------------
# Shared libraries  --enable-shared=yes		yes
# Static libraries  --enable-static=yes		yes
# Build utilities   --with-utilities=yes		yes
# Module support    --with-modules=no		no
# GNU ld            --with-gnu-ld=yes		yes
# Quantum depth     --with-quantum-depth=16	16
# High Dynamic Range Imagery
#                   --enable-hdri=yes		yes
# 
# Install documentation:				yes
# 
# Memory allocation library:
#   JEMalloc          --with-jemalloc=yes		yes
#   TCMalloc          --with-tcmalloc=yes		yes
#   UMem              --with-umem=no		no
# 
# Delegate library configuration:
#   BZLIB             --with-bzlib=yes		yes
#   Autotrace         --with-autotrace=no		no
#   DJVU              --with-djvu=yes		yes
#   DPS               --with-dps=yes		no
#   FFTW              --with-fftw=yes		yes
#   FLIF              --with-flif=yes		no
#   FlashPIX          --with-fpx=yes		no
#   FontConfig        --with-fontconfig=yes	yes
#   FreeType          --with-freetype=yes		yes
#   Ghostscript lib   --with-gslib=yes		yes
#   Graphviz          --with-gvc=yes		yes
#   HEIC              --with-heic=yes		yes
#   JBIG              --with-jbig=yes		yes
#   JPEG v1           --with-jpeg=yes		yes
#   JPEG XL           --with-jxl=yes		no
#   LCMS              --with-lcms=yes		yes
#   LQR               --with-lqr=yes		yes
#   LTDL              --with-ltdl=no		no
#   LZMA              --with-lzma=yes		yes
#   Magick++          --with-magick-plus-plus=yes	yes
#   OpenEXR           --with-openexr=yes		yes
#   OpenJP2           --with-openjp2=yes		yes
#   PANGO             --with-pango=yes		yes
#   PERL              --with-perl=yes		/usr/bin/perl
#   PNG               --with-png=yes		yes
#   RAQM              --with-raqm=yes		yes
#   RAW               --with-raw=yes		yes
#   RSVG              --with-rsvg=yes		yes
#   TIFF              --with-tiff=yes		yes
#   WEBP              --with-webp=yes		yes
#   WMF               --with-wmf=yes		yes
#   X11               --with-x=yes			yes
#   XML               --with-xml=yes		yes
#   ZIP               --with-zip=yes		yes
#   ZLIB              --with-zlib=yes		yes
#   ZSTD              --with-zstd=yes		yes
# 
# Delegate program configuration:
#   GhostPCL          None			pcl6 (unknown)
#   GhostXPS          None			gxps (unknown)
#   Ghostscript       None			gs (9.50)
# 
# Font configuration:
#   Apple fonts       --with-apple-font-dir=default	
#   Dejavu fonts      --with-dejavu-font-dir=default	none
#   Ghostscript fonts --with-gs-font-dir=default		/usr/share/ghostscript/fonts/
#   URW-base35 fonts  --with-urw-base35-font-dir=default	none
#   Windows fonts     --with-windows-font-dir=default	none
# 
# X11 configuration:
#   X_CFLAGS        = 
#   X_PRE_LIBS      =  -lSM -lICE
#   X_LIBS          = 
#   X_EXTRA_LIBS    = 
# 
# Options used to compile and link:
#   PREFIX          = /usr/local
#   EXEC-PREFIX     = /usr/local
#   VERSION         = 7.1.0-54
#   CC              = gcc
#   CFLAGS          = -I/usr/include/libxml2   -I/usr/include/x86_64-linux-gnu -I/usr/include/cairo -I/usr/include/glib-2.0 -I/usr/lib/x86_64-linux-gnu/glib-2.0/include -I/usr/include/pixman-1 -I/usr/include/uuid -I/usr/include/freetype2 -I/usr/include/libpng16 -pthread -I/usr/include/librsvg-2.0 -I/usr/include/gdk-pixbuf-2.0 -I/usr/include/libmount -I/usr/include/blkid -I/usr/include/cairo -I/usr/include/glib-2.0 -I/usr/lib/x86_64-linux-gnu/glib-2.0/include -I/usr/include/pixman-1 -I/usr/include/uuid -I/usr/include/freetype2 -I/usr/include/libpng16 -I/usr/include/libraw -I/usr/include/libpng16  -I/usr/include/pango-1.0 -I/usr/include/fribidi -I/usr/include/harfbuzz -I/usr/include/cairo -I/usr/include/glib-2.0 -I/usr/lib/x86_64-linux-gnu/glib-2.0/include -I/usr/include/pixman-1 -I/usr/include/uuid -I/usr/include/freetype2 -I/usr/include/libpng16 -I/usr/include/OpenEXR  -I/usr/include/lqr-1 -I/usr/include/glib-2.0 -I/usr/lib/x86_64-linux-gnu/glib-2.0/include -I/usr/include/openjpeg-2.3   -I/usr/include/graphviz -I/usr/include/harfbuzz -I/usr/include/glib-2.0 -I/usr/lib/x86_64-linux-gnu/glib-2.0/include -I/usr/include/fribidi -I/usr/include/freetype2 -I/usr/include/libpng16 -I/usr/include/freetype2 -I/usr/include/libpng16 -I/usr/include/uuid -I/usr/include/freetype2 -I/usr/include/libpng16 -pthread     -fopenmp -Wall -g -O2 -mtune=native -fexceptions -pthread -DMAGICKCORE_HDRI_ENABLE=1 -DMAGICKCORE_QUANTUM_DEPTH=16 -fno-builtin-malloc -fno-builtin-calloc -fno-builtin-realloc -fno-builtin-free
#   CPPFLAGS        =  -DMAGICKCORE_HDRI_ENABLE=1 -DMAGICKCORE_QUANTUM_DEPTH=16 
#   PCFLAGS         = 
#   DEFS            = -DHAVE_CONFIG_H
#   LDFLAGS         =  
#   LIBS            =  -ljbig -llcms2 -ltiff -lfreetype -lraqm -lfreetype -ljpeg  -lgs -llqr-1 -lglib-2.0 -lpng16 -lz  -ldjvulibre -lfftw3   -lfontconfig -lfreetype -lheif -lwebpmux -lwebpdemux -lwebp -lwebp -lwmflite  -lXext -lXt   -lSM -lICE -lX11  -llzma -lbz2 -lIlmImf -lImath -lHalf -lIex -lIexMath -lIlmThread -lpthread -lopenjp2 -lpangocairo-1.0 -lpango-1.0 -lgobject-2.0 -lglib-2.0 -lharfbuzz -lcairo -lraw_r -lstdc++ -fopenmp -llcms2 -lrsvg-2 -lm -lgio-2.0 -lgdk_pixbuf-2.0 -lgobject-2.0 -lglib-2.0 -lcairo -lxml2 -lgvc -lcgraph -lcdt -lz -L/usr//usr/lib/x86_64-linux-gnu -lzip    -lm   -ljemalloc -lpthread -ltcmalloc_minimal
#   CXX             = g++
#   CXXFLAGS        =  -pthread
#   FEATURES        = DPC HDRI Cipher OpenMP
#   DELEGATES       = bzlib djvu fftw fontconfig freetype gslib heic jbig jng jpeg lcms lqr lzma openexr openjp2 pango png ps raqm raw rsvg tiff video webp wmf x xml zip zlib zstd
# ==============================================================================
# 
make -j 8
sudo make install
sudo ldconfig /usr/local/lib

# check
identify --version

# Version: ImageMagick 7.1.0-54 Q16-HDRI x86_64 ee2f46b29:20221210 https://imagemagick.org
# Copyright: (C) 1999 ImageMagick Studio LLC
# License: https://imagemagick.org/script/license.php
# Features: Cipher DPC HDRI OpenMP(4.5) TCMalloc 
# Delegates (built-in): bzlib cairo djvu fftw fontconfig freetype gslib gvc heic jbig jng jp2 jpeg lcms lqr lzma openexr pangocairo png ps raqm raw rsvg tiff webp wmf x xml zip zlib
# Compiler: gcc (9.4)
