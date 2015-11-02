SBT = sbt

compile: sbt
	$(SBT) compile

check: sbt
	$(SBT) test

clean: sbt
	$(SBT) clean



sbt:
	@ command -v $(SBT) >/dev/null 2>&1 || \
	{ echo >&2 "I require '$(SBT)' but it's not installed.  Aborting."; exit 1;}
