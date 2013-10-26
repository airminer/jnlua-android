
LOCAL_MODULE := libjnlua
LOCAL_C_INCLUDES += $(NDK_APP_PROJECT_PATH)/jni/lua-5.1.5/src
LOCAL_SRC_FILES := jnlua/src/jnlua.c lua-5.1.5/src/lapi.c lua-5.1.5/src/lauxlib.c lua-5.1.5/src/lbaselib.c lua-5.1.5/src/lcode.c lua-5.1.5/src/ldblib.c lua-5.1.5/src/ldebug.c lua-5.1.5/src/ldo.c lua-5.1.5/src/ldump.c lua-5.1.5/src/lfunc.c lua-5.1.5/src/lgc.c lua-5.1.5/src/linit.c lua-5.1.5/src/liolib.c lua-5.1.5/src/llex.c lua-5.1.5/src/lmathlib.c lua-5.1.5/src/lmem.c lua-5.1.5/src/loadlib.c lua-5.1.5/src/lobject.c lua-5.1.5/src/lopcodes.c lua-5.1.5/src/loslib.c lua-5.1.5/src/lparser.c lua-5.1.5/src/lstate.c lua-5.1.5/src/lstring.c lua-5.1.5/src/lstrlib.c lua-5.1.5/src/ltable.c lua-5.1.5/src/ltablib.c lua-5.1.5/src/ltm.c lua-5.1.5/src/lundump.c lua-5.1.5/src/lvm.c lua-5.1.5/src/lzio.c lua-5.1.5/src/print.c
	
LOCAL_LDLIBS := -llog

# POSIX as we're on linux, and compatibility mode in case you'll be running scripts written for LUA <5.2
LOCAL_CFLAGS += -DLUA_USE_POSIX -DLUA_COMPAT_ALL

include $(BUILD_SHARED_LIBRARY)

 