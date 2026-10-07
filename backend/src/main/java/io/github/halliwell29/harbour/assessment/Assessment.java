package io.github.halliwell29.harbour.assessment;

import java.util.List;

public record Assessment(OperatingStatus status, List<String> reasons) { }
