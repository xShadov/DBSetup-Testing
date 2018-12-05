package com.shadov.test.dbsetup;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;

class CommonOperations {
	static final Operation DELETE_ALL = deleteAllFrom("USER");
}
